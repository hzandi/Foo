package com.sample.data.api.repository

import com.sample.data.entity.LatestExRateEntity

interface ExchangeRateRepository {

    suspend fun getLatestExchangeRate(
        base: String,
        symbols: String
    ): LatestExRateEntity

}
