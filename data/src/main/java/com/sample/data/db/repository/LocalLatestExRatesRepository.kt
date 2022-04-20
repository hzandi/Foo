package com.sample.data.db.repository

import com.sample.data.entity.LatestExRateEntity

interface LocalLatestExRatesRepository {

    suspend fun insertLatestExchangeRates(latestExRateEntity: LatestExRateEntity)

    suspend fun getLatestExchangeRates(): LatestExRateEntity?

    suspend fun deleteLatestExchangeRates()
}
