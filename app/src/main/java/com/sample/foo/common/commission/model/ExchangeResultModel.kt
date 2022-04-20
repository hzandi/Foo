package com.sample.foo.common.commission.model

data class ExchangeResultModel(
    val result: Double,
    val fee: Double,
    val sellSymbol: String,
    val buySymbol: String,
)
