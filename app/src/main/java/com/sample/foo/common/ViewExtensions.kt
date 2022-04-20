package com.sample.foo.common

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

private const val ANIM_DURATION_MS: Long = 1000

fun View.fadeIn() {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(ANIM_DURATION_MS)
        .setListener(null)
}

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus
    view?.let {
        it.clearFocus()
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
