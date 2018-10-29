package com.example.thanhhoang.qlcosovatchat.ui.login

import com.example.thanhhoang.qlcosovatchat.data.model.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class LoginViewModel(private val repository: Repository) {
    private val behaviorButtonLoginState = BehaviorSubject.create<Boolean>()
    private val behaviorHandleLogin = BehaviorSubject.create<Boolean>()

    fun handleValidLoginFieldsState(): BehaviorSubject<Boolean> = behaviorButtonLoginState

    fun validateLoginFields(username: String, password: String) {
        behaviorButtonLoginState.onNext(!username.isEmpty() && !password.isEmpty())
    }

    fun login(userRequest: UserRequest): Single<LoginResponse> {
        return repository.login(userRequest)
                .doOnSubscribe {
                    behaviorHandleLogin.onNext(true)
                }
                .doFinally {
                    behaviorHandleLogin.onNext(false)
                }
    }
}
