package com.sample.foo.common

import retrofit2.HttpException
import java.io.IOException

object ExceptionHumanizer {

    private const val NO_INTERNET_CONNECTION = "No Internet connection"
    private const val SOMETHING_WENT_WRONG = "Something went wrong"
    private const val BASE_CURRENCY_ACCESS_RESTRICTED = "base currency access restricted"

    fun getHumanizedErrorMessage(t: Throwable): String {
        return when (t) {
            is IOException -> NO_INTERNET_CONNECTION
            is HttpException -> BASE_CURRENCY_ACCESS_RESTRICTED
            else -> SOMETHING_WENT_WRONG
        }
    }

}
