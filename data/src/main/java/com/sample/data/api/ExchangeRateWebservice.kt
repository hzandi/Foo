package com.sample.data.api

import com.sample.data.entity.LatestExRateEntity
import retrofit2.http.GET

interface ExchangeRateWebservice {

    @GET("v1/latest")
    suspend fun getLatestExRates(
    ): LatestExRateEntity

}
