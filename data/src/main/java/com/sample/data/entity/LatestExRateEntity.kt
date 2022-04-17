package com.sample.data.entity

import com.google.gson.annotations.SerializedName

data class LatestExRateEntity(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("timestamp")
    val timestamp: String?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("rates")
    val rates: HashMap<String, Double>?
)

