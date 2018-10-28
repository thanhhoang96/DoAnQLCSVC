package com.example.thanhhoang.qlcosovatchat.ui.splash

import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository

class SplashViewModel(private val repository: Repository) {

    internal fun hasAccessToken(): Boolean = repository.hasAccessToken()
}
