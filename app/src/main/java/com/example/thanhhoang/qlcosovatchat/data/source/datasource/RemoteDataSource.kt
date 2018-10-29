package com.example.thanhhoang.qlcosovatchat.data.source.datasource

import com.example.thanhhoang.qlcosovatchat.data.model.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import io.reactivex.Single

interface RemoteDataSource {
    fun login(userRequest: UserRequest): Single<LoginResponse>
}
