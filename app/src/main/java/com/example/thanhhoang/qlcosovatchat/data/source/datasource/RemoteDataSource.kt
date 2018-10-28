package com.example.thanhhoang.qlcosovatchat.data.source.datasource

import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import io.reactivex.Single

interface RemoteDataSource {
    fun login(userName: String, password: String): Single<LoginResponse>
}
