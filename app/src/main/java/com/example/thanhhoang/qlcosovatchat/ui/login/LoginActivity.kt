package com.example.thanhhoang.qlcosovatchat.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.extention.afterTextChanged
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initListener()
        handleReceiveEvent()
    }

    private fun initView() {
        edtUsername.setText("thanhhoang")
        edtPassword.setText("1996")
    }

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

        btnLogin.setOnClickListener {
            Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
            if ((edtUsername.text.toString() == "thanhhoang") && (edtPassword.text.toString() == "1996")) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", edtUsername.text.toString() )
                startActivity(intent)
            }
        }
    }

    private fun eventEditTextTextChange(username: String, password: String) {
        viewModel.validateLoginFields(username, password)
    }
}
