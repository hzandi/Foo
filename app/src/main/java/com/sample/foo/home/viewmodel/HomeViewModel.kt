package com.sample.foo.home.viewmodel

import com.sample.data.entity.LatestExRateEntity
import com.sample.data.repository.ExchangeRateRepository
import com.sample.foo.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) : BaseViewModel<LatestExRateEntity>() {

    fun getLatestExchangeRates() {

    }
}

