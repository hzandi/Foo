package com.sample.data.repository

import com.sample.data.entity.LatestExRateEntity

interface ExchangeRateRepository {

    suspend fun getLatestExchangeRate(): LatestExRateEntity

}
