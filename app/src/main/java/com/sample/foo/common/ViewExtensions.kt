package com.sample.foo.common

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.transition.TransitionManager
import com.transitionseverywhere.ChangeText

private const val ANIM_DURATION_MS: Long = 1000

fun View.fadeIn() {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(ANIM_DURATION_MS)
        .setListener(null)
}

fun TextView.setText(container: ViewGroup, text: String) {
    // TODO check to see if we could reuse the following line
    val transition = ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_IN)
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(container, transition)
    this.text = text
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
