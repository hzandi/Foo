package com.sample.foo.home.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sample.data.entity.BalanceEntity
import com.sample.data.entity.LatestExRateEntity
import com.sample.foo.R
import com.sample.foo.common.DurationTracker
import com.sample.foo.common.ViewState
import com.sample.foo.common.commission.CommissionType
import com.sample.foo.common.commission.model.ExchangeRequestModel
import com.sample.foo.common.commission.model.ExchangeResultModel
import com.sample.foo.databinding.FragmentHomeBinding
import com.sample.foo.home.view.adapter.BalanceAdapter
import com.sample.foo.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class HomeFragment : Fragment(), CoroutineScope {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var snackbar: Snackbar
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    @Inject
    lateinit var durationTracker: DurationTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // observe duration tracker
        lifecycle.addObserver(durationTracker)

        // handle back press in fragment
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeViewModel()

        homeViewModel.getLatestExchangeRates()

//        // start currency rate syncer
//        durationTracker.setTaskWithInterval(object : DurationTracker.OnTaskEvent {
//            override fun execute() {
//                homeViewModel.syncLatestExchangeRates()
//            }
//        }, 5).startTimer()
    }

    private fun setupViews() {
        binding.sellEditText.requestFocus()
        binding.sellEditText.addTextChangedListener(debounceWatcher)
    }

    private fun observeViewModel() {
        homeViewModel.getViewState()
            .observe(viewLifecycleOwner) { viewState: ViewState<LatestExRateEntity> ->
                when (viewState) {
                    is ViewState.LoadingState -> showLoading()
                    is ViewState.DataLoadedState -> handleDataLoadedState(viewState)
                    is ViewState.ErrorState -> handleErrorState(viewState)
                }
            }

        homeViewModel.balancesLiveData
            .observe(viewLifecycleOwner) { viewState: ViewState<List<BalanceEntity>> ->
                when(viewState){
                    is ViewState.LoadingState -> showBalanceLoading()
                    is ViewState.DataLoadedState -> handleBalanceLoadedState(viewState)
                    else -> {}
                }
            }

        homeViewModel.exchangeLiveData
            .observe(viewLifecycleOwner) { viewState: ViewState<ExchangeResultModel> ->
                sequenceOf(
                    when (viewState) {
                        is ViewState.DataLoadedState -> handleExchangePreviewState(viewState)
                        is ViewState.ErrorState -> handleExchangePreviewErrorState(viewState)
                        else -> {}
                    }
                )
            }
    }

    private fun showBalanceLoading() {
        binding.balanceProgressBar.isVisible = true
        binding.balanceRecyclerView.isGone = true
    }

    private fun handleBalanceLoadedState(viewState: ViewState.DataLoadedState<List<BalanceEntity>>) {
        binding.balanceProgressBar.isGone = true
        binding.balanceRecyclerView.isVisible = true

        if(viewState.data.isNotEmpty()) {
            initBalanceRecyclerView(viewState.data)
        }
    }

    private fun initBalanceRecyclerView(currencies: List<BalanceEntity>) {
        val recyclerView = binding.balanceRecyclerView
        val adapter = BalanceAdapter(currencies)
        val gridLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.contentConstraintLayout.isGone = true
    }

    private fun handleDataLoadedState(viewState: ViewState.DataLoadedState<LatestExRateEntity>) {
        binding.progressBar.isGone = true
        binding.contentConstraintLayout.isVisible = true

        viewState.data.rates?.let { rates ->
            val currencies = ArrayList(rates.keys)

            initSellCurrencySpinner(currencies)
            initBuyCurrencySpinner(currencies)

            homeViewModel.getLocalBalances()
        }

    }

    private fun handleExchangePreviewState(viewState: ViewState.DataLoadedState<ExchangeResultModel>) {
        val resultValue = "+" + viewState.data.result.toString()
        binding.buyTextView.text = resultValue
    }

    private fun handleErrorState(viewState: ViewState.ErrorState<LatestExRateEntity>) {
        showSnackbar(viewState.errorMessage)
    }

    private fun handleExchangePreviewErrorState(viewState: ViewState.ErrorState<ExchangeResultModel>) {
        binding.buyTextView.text = viewState.errorMessage
    }

    private fun showSnackbar(message: String) {
        binding.progressBar.isGone = true
        if (homeViewModel.hasFirstExchangeRates) {
            binding.contentConstraintLayout.isVisible = true
        }
        snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                homeViewModel.getLatestExchangeRates()
            }
        snackbar.show()
    }

    private fun initSellCurrencySpinner(currencies: ArrayList<String>) {

        val currencyAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_layout,
            currencies
        )
        binding.sellCurrencySpinner.adapter = currencyAdapter

        binding.sellCurrencySpinnerContainer.setOnClickListener {
            binding.sellCurrencySpinnerContainer.post {
                binding.sellCurrencySpinner.performClick()
            }
        }

        // spinner set ItemSelectedListener
        binding.sellCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCurrency = currencies[position]
                    binding.sellCurrencyTextView.text = selectedCurrency
                    if (selectedCurrency != homeViewModel.baseCurrency) {
                        snackbar.dismiss()
                        homeViewModel.getLatestExchangeRates(selectedCurrency)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    binding.sellCurrencyTextView.text = currencies[0]
                }
            }

        // default selected value
        if (currencies.isNotEmpty()) {
            val indexOfBaseCurrency = currencies.indexOf(homeViewModel.baseCurrency)
            binding.sellCurrencySpinner.setSelection(indexOfBaseCurrency)
        }
    }

    private fun initBuyCurrencySpinner(currencies: ArrayList<String>) {

        // remove base currency
        val buyCurrencies = ArrayList(currencies)
        if (buyCurrencies.contains(homeViewModel.baseCurrency)) {
            buyCurrencies.remove(homeViewModel.baseCurrency)
        }

        // adapter
        val currencyAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_layout,
            buyCurrencies
        )
        binding.buyCurrencySpinner.adapter = currencyAdapter

        binding.buyCurrencySpinnerContainer.setOnClickListener {
            binding.buyCurrencySpinnerContainer.post {
                binding.buyCurrencySpinner.performClick()
            }
        }

        // spinner set ItemSelectedListener
        binding.buyCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.buyCurrencyTextView.text = buyCurrencies[position]
                    binding.sellEditText.text?.let {
                        if (it.isNotEmpty()) {
                            callExchange()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    binding.buyCurrencyTextView.text = buyCurrencies[0]
                }
            }

        // default selected value
        if (buyCurrencies.isNotEmpty()) {
            if (currencies[0] == homeViewModel.baseCurrency) {
                binding.buyCurrencySpinner.setSelection(1)
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            binding.progressBar.isGone = true
            binding.contentConstraintLayout.isVisible = true
        }
    }

    // debounce editText watcher
    private val debounceWatcher = object : TextWatcher {
        private var searchFor = ""

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText == searchFor)
                return

            searchFor = searchText
            launch {
                delay(500)  //debounce timeOut
                if (searchText != searchFor)
                    return@launch

                // call exchange
                callExchange()
            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    }

    private fun callExchange() {
        binding.sellEditText.text?.toString()?.let { sellValue ->
            if(sellValue.isEmpty()) {
                binding.buyTextView.text = ""
                return
            }
            val exchangeRequestModel = ExchangeRequestModel(
                sellValue.toDouble(),
                null,
                CommissionType.FIXED_FEE,
                binding.sellCurrencyTextView.text.toString(),
                binding.buyCurrencyTextView.text.toString()
            )
            homeViewModel.exchange(exchangeRequestModel)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
