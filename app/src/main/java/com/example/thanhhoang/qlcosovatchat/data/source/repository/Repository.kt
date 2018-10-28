package com.example.thanhhoang.qlcosovatchat.data.source.repository

import android.content.Context
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.LocalDataSource
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class Repository(context: Context): LocalDataSource, RemoteDataSource{

    private val localDataSource = LocalRepository(context)
    private val remoteRepository = RemoteRepository(context)

    override fun login(userName: String, password: String): Single<LoginResponse> =
            remoteRepository.login(userName,password).doOnSuccess {
                localDataSource.saveAccessToken(it.data.token)
                localDataSource.saveInfoUser(it.data.user.userName, it.data.user.fullName)
            }

    override fun isValidToken(token: String): Boolean = localDataSource.isValidToken(token)

    override fun hasAccessToken(): Boolean = localDataSource.hasAccessToken()
}
