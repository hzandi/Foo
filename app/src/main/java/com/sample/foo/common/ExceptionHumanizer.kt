package com.sample.foo.common

import java.io.IOException

object ExceptionHumanizer {

    private const val NO_INTERNET_CONNECTION = "No Internet connection"
    private const val SOMETHING_WENT_WRONG = "Something went wrong"

    fun getHumanizedErrorMessage(t: Throwable): String {
        return when (t) {
            is IOException -> NO_INTERNET_CONNECTION
            else -> SOMETHING_WENT_WRONG
        }
    }

}
