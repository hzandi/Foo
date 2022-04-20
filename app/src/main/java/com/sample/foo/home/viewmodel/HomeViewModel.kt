package com.sample.foo.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.data.entity.LatestExRateEntity
import com.sample.data.api.repository.ExchangeRateRepository
import com.sample.data.db.repository.LocalBalanceRepository
import com.sample.data.db.repository.LocalLatestExRatesRepository
import com.sample.data.entity.BalanceEntity
import com.sample.foo.common.BaseViewModel
import com.sample.foo.common.ExceptionHumanizer
import com.sample.foo.common.ViewState
import com.sample.foo.common.commission.Exchanger
import com.sample.foo.common.commission.model.ExchangeRequestModel
import com.sample.foo.common.commission.model.ExchangeResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val localBalanceRepository: LocalBalanceRepository,
    private val localLatestExRatesRepository: LocalLatestExRatesRepository,
    private val exchanger: Exchanger
) : BaseViewModel<LatestExRateEntity>() {

    private val supportedCurrency =
        arrayListOf("EUR", "USD", "CHF", "SEK", "AUD", "CAD", "TRY", "AED")
    private var _baseCurrency: String = "EUR"
    val baseCurrency: String
        get() = _baseCurrency
    private var _hasFirstExchangeRates: Boolean = false
    val hasFirstExchangeRates: Boolean
        get() = _hasFirstExchangeRates
    private var balances = ArrayList<BalanceEntity>()
    private var _balancesMutableLiveData = MutableLiveData<ViewState<List<BalanceEntity>>>()
    val balancesLiveData: LiveData<ViewState<List<BalanceEntity>>> = _balancesMutableLiveData
    private var _exchangeMutableLiveData = MutableLiveData<ViewState<ExchangeResultModel>>()
    val exchangeLiveData: LiveData<ViewState<ExchangeResultModel>> = _exchangeMutableLiveData

    fun getLatestExchangeRates(base: String = _baseCurrency) {
        setViewState(ViewState.loading())

        _baseCurrency = base
        val symbols = supportedCurrency.joinToString(",")

        viewModelScope.launch {
            try {
                val response: LatestExRateEntity =
                    exchangeRateRepository.getLatestExchangeRate(_baseCurrency, symbols)
                if (response.success == true) {
                    setViewState(ViewState.success(response))
                    saveLatestExRates(response)
                    _hasFirstExchangeRates = true
                } else {
                    response.error?.info?.let { info ->
                        setViewState(ViewState.error(info))
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                val errorMessage = ExceptionHumanizer.getHumanizedErrorMessage(t)
                setViewState(ViewState.error(errorMessage))
            }
        }

    }

    fun syncLatestExchangeRates() {

        val symbols = supportedCurrency.joinToString(",")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: LatestExRateEntity =
                    exchangeRateRepository.getLatestExchangeRate(_baseCurrency, symbols)
                if (response.success == true) {
                    saveLatestExRates(response)
                    _hasFirstExchangeRates = true
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun exchange(exchangeRequestModel: ExchangeRequestModel){

        viewModelScope.launch {

            // handle empty or wrong sell values
            getLocalBalances()
            for(balance in balances){
                if(balance.symbol == exchangeRequestModel.sellSymbol){
                    if(exchangeRequestModel.value <= 0 ||
                        exchangeRequestModel.value > balance.value ?: 0.0) {
                        _exchangeMutableLiveData.value = ViewState.ErrorState("Wrong Sell Value!")
                        return@launch
                    }
                }
            }

            // exchange preview
            val latestExRateEntity = localLatestExRatesRepository.getLatestExchangeRates()
            latestExRateEntity?.rates?.let { exchangeRates ->
                exchangeRequestModel.exchangeRate = exchangeRates[exchangeRequestModel.buySymbol]
            }
            exchanger.change(exchangeRequestModel)?.let {
                _exchangeMutableLiveData.value = ViewState.success(it)
            }
        }
    }

    fun saveLatestExRates(latestExRateEntity: LatestExRateEntity?) {
        viewModelScope.launch(Dispatchers.IO) {
            latestExRateEntity?.let {
                localLatestExRatesRepository.deleteLatestExchangeRates()
                localLatestExRatesRepository.insertLatestExchangeRates(
                    latestExRateEntity
                )
            }
        }
    }

    fun getLocalBalances() {
        viewModelScope.launch(Dispatchers.Main) {
            _balancesMutableLiveData.value = ViewState.loading()
        }

        viewModelScope.launch(Dispatchers.IO) {
            val balanceEntities = localBalanceRepository.getBalances()
            viewModelScope.launch(Dispatchers.Main) {
                balanceEntities?.let {
                    if(it.isNotEmpty()) {
                        _balancesMutableLiveData.value = ViewState.success(it)
                        balances = ArrayList(it)
                    }else {
                        initLocalBalances()
                    }
                } ?: run {
                    _balancesMutableLiveData.value = ViewState.success(ArrayList())
                }
            }
        }
    }

    fun updateLocalBalances(balanceEntity: BalanceEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            localBalanceRepository.insertOrUpdateBalance(balanceEntity)
        }
    }

    private fun initLocalBalances() {
        viewModelScope.launch(Dispatchers.IO) {
            localBalanceRepository.insertOrUpdateBalance(
                BalanceEntity("EUR", 1000.00)
            )
            val symbols = ArrayList(supportedCurrency)
            symbols.remove("EUR")
            for (symbol in symbols) {
                localBalanceRepository.insertOrUpdateBalance(
                    BalanceEntity(symbol, 0.00)
                )
            }
            getLocalBalances()
        }
    }
}

