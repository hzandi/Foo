package com.sample.data.repository

import com.sample.data.api.ExchangeRateWebservice
import com.sample.data.entity.LatestExRateEntity
import javax.inject.Inject

class ExchangeRateRepositoryImpl
@Inject constructor(
    private val exchangeRateWebservice: ExchangeRateWebservice,
) : ExchangeRateRepository {

    override suspend fun getLatestExchangeRate(): LatestExRateEntity {
        return exchangeRateWebservice.getLatestExRates()
    }
}
