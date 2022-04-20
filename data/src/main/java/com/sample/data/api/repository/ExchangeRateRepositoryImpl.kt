package com.sample.data.api.repository

import com.sample.data.api.ExchangeRateWebservice
import com.sample.data.entity.LatestExRateEntity
import javax.inject.Inject

class ExchangeRateRepositoryImpl
@Inject constructor(
    private val exchangeRateWebservice: ExchangeRateWebservice,
) : ExchangeRateRepository {

    override suspend fun getLatestExchangeRate(
        base: String,
        symbols: String
    ): LatestExRateEntity {
        return exchangeRateWebservice.getLatestExRates(
            base, symbols
        )
    }
}
