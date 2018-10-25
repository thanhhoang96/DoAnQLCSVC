package com.example.thanhhoang.qlcosovatchat.ui.login

import io.reactivex.subjects.BehaviorSubject

class LoginViewModel {
    private val behaviorButtonLogin = BehaviorSubject.create<Boolean>()

    fun handleValidLoginFieldsState(): BehaviorSubject<Boolean> = behaviorButtonLogin

    fun validateLoginFields(username: String, password: String) {
        behaviorButtonLogin.onNext(!username.isEmpty() && !password.isEmpty())
    }
}