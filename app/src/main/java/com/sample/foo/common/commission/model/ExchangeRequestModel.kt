package com.sample.foo.common.commission.model

import com.sample.foo.common.commission.CommissionType

data class ExchangeRequestModel(
    val value: Double,
    val exchangeRate: Double,
    val commissionType: CommissionType,
    val sellSymbol: String,
    val buySymbol: String
)
