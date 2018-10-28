package com.example.thanhhoang.qlcosovatchat.data.source.repository

import android.content.Context
import com.example.thanhhoang.qlcosovatchat.api.ApiService
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class RemoteRepository(private val context: Context): RemoteDataSource {

    private val apiService = ApiService.create()

    override fun login(userName: String, password: String): Single<LoginResponse> = apiService.login(userName, password)
}
