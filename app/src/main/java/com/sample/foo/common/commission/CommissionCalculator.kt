package com.sample.foo.common.commission

import com.sample.data.pref.LocalStorage
import com.sample.foo.common.commission.model.ExchangeRequestModel
import com.sample.foo.common.commission.model.ExchangeResultModel
import javax.inject.Inject

// (facade pattern) A class to hide complexity from ViewModel
class CommissionCalculator @Inject constructor(
    private val exchangeWithFixedFee: ExchangeWithFixedFee,
    private val exchangeWithFixedFeeAfterNFree: ExchangeWithFixedFeeAfterNFree,
    private val exchangeWithEveryNthFree: ExchangeWithEveryNthFree,
    private val exchangeWithFixedFeeUpToNPriceFree: ExchangeWithFixedFeeUpToNPriceFree
){

    fun parse(exchangeRequestModel: ExchangeRequestModel): ExchangeResultModel {
        return when(exchangeRequestModel.commissionType){
            CommissionType.FIXED_FEE -> exchangeWithFixedFee.change(exchangeRequestModel)
            CommissionType.FIXED_FEE_START_WITH_N_FREE -> exchangeWithFixedFeeAfterNFree.change(exchangeRequestModel)
            CommissionType.EVERY_N_CONVERSION_FREE -> exchangeWithEveryNthFree.change(exchangeRequestModel)
            CommissionType.UP_TO_N_PRICE_CONVERSION_FREE -> exchangeWithFixedFeeUpToNPriceFree.change(exchangeRequestModel)
        }
    }
}

interface ExchangeRule {
    fun change(exchangeRequestModel: ExchangeRequestModel): ExchangeResultModel
}

// calc result with fixed 0.07 % fee
class ExchangeWithFixedFee @Inject constructor() : ExchangeRule {

    companion object {
        const val FEE_PERCENT: Double = 0.07
    }

    override fun change(exchangeRequestModel: ExchangeRequestModel): ExchangeResultModel {
        val fee = exchangeRequestModel.value * FEE_PERCENT
        val result = (exchangeRequestModel.value - fee) * exchangeRequestModel.exchangeRate
        return ExchangeResultModel(
            result, fee,
            exchangeRequestModel.sellSymbol,
            exchangeRequestModel.buySymbol
        )
    }
}

// calc result with fixed 0.07 % fee from n free exchange
class ExchangeWithFixedFeeAfterNFree @Inject constructor(
    private val localStorage: LocalStorage
) : ExchangeRule {

    companion object {
        const val FEE_PERCENT: Double = 0.07
        const val N = 5
        const val KEY = "fixed_fee_after_n_free"
    }

    override fun change(exchangeRequestModel: ExchangeRequestModel): ExchangeResultModel {
        val n = localStorage.getInt(KEY) ?: N
        var fee = 0.0
        if(n == 0) {
            fee = exchangeRequestModel.value * FEE_PERCENT
        }else{
            localStorage.putInt(KEY, n - 1)
        }
        val result = (exchangeRequestModel.value - fee) * exchangeRequestModel.exchangeRate
        return ExchangeResultModel(
            result, fee,
            exchangeRequestModel.sellSymbol,
            exchangeRequestModel.buySymbol
        )
    }
}

// calc result with fixed 0.07 % fee with every n free exchange
class ExchangeWithEveryNthFree @Inject constructor(
    private val localStorage: LocalStorage
) : ExchangeRule {

    companion object {
        const val FEE_PERCENT: Double = 0.07
        const val NTH = 10
        const val KEY = "with_every_nth_free"
    }

    override fun change(exchangeRequestModel: ExchangeRequestModel): ExchangeResultModel {
        val n = localStorage.getInt(KEY) ?: 0
        var fee = 0.0
        if(n % NTH != 0) {
            fee = exchangeRequestModel.value * FEE_PERCENT
        }
        localStorage.putInt(KEY, n + 1)
        val result = (exchangeRequestModel.value - fee) * exchangeRequestModel.exchangeRate
        return ExchangeResultModel(
            result, fee,
            exchangeRequestModel.sellSymbol,
            exchangeRequestModel.buySymbol
        )
    }
}

// calc result with fixed 0.07 % fee with up to n price free conversion
class ExchangeWithFixedFeeUpToNPriceFree @Inject constructor(
    val localStorage: LocalStorage
) : ExchangeRule {

    companion object {
        const val FEE_PERCENT: Double = 0.07
        const val PRICE = 200
    }

    override fun change(exchangeRequestModel: ExchangeRequestModel): ExchangeResultModel {
        var fee = exchangeRequestModel.value * FEE_PERCENT
        if(exchangeRequestModel.value >= PRICE) {
            fee = 0.0
        }
        val result = (exchangeRequestModel.value - fee) * exchangeRequestModel.exchangeRate
        return ExchangeResultModel(
            result, fee,
            exchangeRequestModel.sellSymbol,
            exchangeRequestModel.buySymbol
        )
    }
}
