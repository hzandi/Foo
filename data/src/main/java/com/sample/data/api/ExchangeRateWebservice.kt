package com.sample.data.api

import com.sample.data.entity.LatestExRateEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateWebservice {

    @GET("v1/latest")
    suspend fun getLatestExRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): LatestExRateEntity

}
