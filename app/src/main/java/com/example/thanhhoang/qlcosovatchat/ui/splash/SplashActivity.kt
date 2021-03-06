package com.example.thanhhoang.qlcosovatchat.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.thanhhoang.qlcosovatchat.MainActivity
import com.example.thanhhoang.qlcosovatchat.R
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import com.example.thanhhoang.qlcosovatchat.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = SplashViewModel(Repository())

        Handler().postDelayed({
            startActivity(Intent(this, if (viewModel.hasAccessToken()) MainActivity::class.java else LoginActivity::class.java))
            finish()
        }, 2000)
    }
}
