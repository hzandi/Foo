package com.sample.data.entity

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T
) {
    fun isSuccessful() = code == 200
}
