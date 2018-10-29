package com.example.thanhhoang.qlcosovatchat.data.source.repository

import android.content.Context
import com.example.thanhhoang.qlcosovatchat.data.model.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.LocalDataSource
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class Repository(context: Context) : LocalDataSource, RemoteDataSource {

    private val localRepository = LocalRepository(context)
    private val remoteRepository = RemoteRepository(context)

    override fun login(userRequest: UserRequest): Single<LoginResponse> =
            remoteRepository.login(userRequest).doOnSuccess {
                localRepository.saveAccessToken(it.data.token)
                localRepository.saveInfoUser(it.data.user.userName, it.data.user.fullName)
            }

    override fun isValidToken(token: String): Boolean = localRepository.isValidToken(token)

    override fun hasAccessToken(): Boolean = localRepository.hasAccessToken()

    fun getFullName() = localRepository.getFullName()
}
