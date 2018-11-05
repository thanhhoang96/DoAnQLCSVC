package com.example.thanhhoang.qlcosovatchat.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import com.example.thanhhoang.qlcosovatchat.extention.moveActivity
import com.example.thanhhoang.qlcosovatchat.util.DialogProgressbarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {
    private val viewModel = LoginViewModel(Repository())
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initListener()
        handleReceiveEvent()
    }

    private fun initView() {
        dialog = DialogProgressbarUtils.showProgressDialog(this)
        dialog?.setCancelable(false)
    }

    @SuppressLint("CheckResult")
    private fun handleReceiveEvent() {
        viewModel.handleValidLoginFieldsState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it == true) {
                        btnLogin.isEnabled = true
                        btnLogin.isFocusable = true
                    } else {
                        btnLogin.isEnabled = false
                        btnLogin.isFocusable = false
                    }
                }, {})
    }

    private fun initListener() {
        edtUsername.afterTextChanged {
            eventEditTextTextChange(edtUsername.text.toString(), edtPassword.text.toString())
        }
        edtPassword.afterTextChanged {
            eventEditTextTextChange(edtUsername.text.toString(), edtPassword.text.toString())
        }

        llContain.setOnTouchListener { _, _ ->
            val inputMethodManager = this.getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    this.currentFocus.windowToken, 0)
        }

        btnLogin.setOnClickListener { _ ->
            dialog?.show()
            Handler().postDelayed({
                viewModel.login(UserRequest(edtUsername.text.toString(), edtPassword.text.toString()))
                        .subscribeOn(Schedulers.io())
                        .doFinally { dialog?.dismiss() }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ handleLoginSuccess() }, { handelLoginError() })
            }, 2000)
        }
    }

    private fun eventEditTextTextChange(username: String, password: String) {
        viewModel.validateLoginFields(username, password)
    }

    private fun handleLoginSuccess() {

        moveActivity(Intent(this, MainActivity::class.java))
    }

    private fun handelLoginError() {
        Toast.makeText(this, "Sorry, Login Failed", Toast.LENGTH_SHORT).show()
        edtUsername.setText("")
        edtPassword.setText("")
    }
}
