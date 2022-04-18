package com.sample.foo.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sample.data.entity.LatestExRateEntity
import com.sample.foo.R
import com.sample.foo.common.DurationTracker
import com.sample.foo.common.ViewState
import com.sample.foo.databinding.FragmentHomeBinding
import com.sample.foo.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var durationTracker: DurationTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(durationTracker)
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
    }

    private fun setupViews() {
        binding.sellEditText.requestFocus()
    }

    private fun observeViewModel(){
        homeViewModel.getViewState()
            .observe(viewLifecycleOwner) { viewState: ViewState<LatestExRateEntity> ->
                when (viewState) {
                    is ViewState.LoadingState -> showLoading()
                    is ViewState.DataLoadedState -> handleDataLoadedState(viewState)
                    is ViewState.ErrorState -> handleErrorState(viewState)
                }
            }
    }

    private fun showLoading() {

    }

    private fun handleDataLoadedState(viewState: ViewState.DataLoadedState<LatestExRateEntity>) {

    }

    private fun handleErrorState(viewState: ViewState.ErrorState<LatestExRateEntity>){
        showSnackbar(viewState.errorMessage)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                homeViewModel.getLatestExchangeRates()
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
