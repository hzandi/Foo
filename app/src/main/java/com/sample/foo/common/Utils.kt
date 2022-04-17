package com.sample.foo.common

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import java.io.File
import kotlin.math.roundToInt
import kotlin.random.Random

object Utils {

    fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).roundToInt()
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).roundToInt()
    }

    fun pxToDp(px: Int, context: Context): Int {
        return (px / context.resources.displayMetrics.density).roundToInt()
    }

    fun closeSoftKeyBoard(activity: Activity?){
        try {
            activity?.let {
                val fragmentActivity = activity as FragmentActivity
                val view = fragmentActivity.currentFocus
                view?.let { focusedView ->
                    val imm = fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
                }
            }
        } catch (ignored: Exception) {}
    }

    fun deleteStoragePrivateFile(filePath: String) {
        val file = File(filePath)
        if(file.exists()) {
            file.delete()
        }
    }

    fun getDisplayWidth(context: Context): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun randomColor(): Int {
        return Color.argb(255,
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256))
    }

}
