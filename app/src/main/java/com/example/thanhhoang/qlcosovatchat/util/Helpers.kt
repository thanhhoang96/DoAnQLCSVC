package com.example.thanhhoang.qlcosovatchat.util

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.security.MessageDigest

object Helpers {
    /**
     * Force show the soft keyboard
     *
     * @param activity the Activity
     * @param view     the current focused view
     */
    fun showSoftKeyboard(activity: Activity, view: View) {
        val handler = Handler()
        handler.postDelayed({
            try {
                val keyboard = activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.showSoftInput(view, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, 400)
    }

    /**
     * Force hide the soft keyboard
     *
     * @param activity the Activity
     * @param view     the current focused view
     */
    fun hideSoftKeyboard(activity: Activity, view: View) {
        try {
            val keyboard = activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}