package com.example.thanhhoang.qlcosovatchat.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {
    private val viewModel = LoginViewModel(Repository(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initListener()
        handleReceiveEvent()
    }

    private fun initView() {}

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
            viewModel.login(edtUsername.text.toString(), edtPassword.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ handleLoginSuccess() }, { handelLoginError() })
        }
    }

    private fun eventEditTextTextChange(username: String, password: String) {
        viewModel.validateLoginFields(username, password)
    }

    private fun handleLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", edtUsername.text.toString())
        startActivity(intent)
    }

    private fun handelLoginError() {
        Toast.makeText(this, "Sorry, Login Failed", Toast.LENGTH_SHORT).show()
        edtUsername.setText("")
        edtPassword.setText("")
    }
}
