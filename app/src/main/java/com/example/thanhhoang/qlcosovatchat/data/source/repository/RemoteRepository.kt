package com.example.thanhhoang.qlcosovatchat.data.source.repository

import android.content.Context
import com.example.thanhhoang.qlcosovatchat.api.ApiService
import com.example.thanhhoang.qlcosovatchat.data.model.User
import com.example.thanhhoang.qlcosovatchat.data.model.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class RemoteRepository(private val context: Context): RemoteDataSource {

    private val apiService = ApiService.create()

    override fun login(userRequest: UserRequest): Single<LoginResponse> = apiService.login(userRequest)
}
