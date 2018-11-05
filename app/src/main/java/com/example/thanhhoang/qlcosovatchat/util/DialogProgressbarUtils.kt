package com.example.thanhhoang.qlcosovatchat.util

import android.app.Dialog
import android.content.Context
import com.example.thanhhoang.qlcosovatchat.R

object DialogProgressbarUtils {

    fun showProgressDialog(context: Context) = Dialog(context, R.style.ProgressDialog).apply {
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_progress)
    }
}
