package com.example.thanhhoang.qlcosovatchat.data.source.repository

import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.LocalDataSource
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class Repository : LocalDataSource, RemoteDataSource {

    private val localRepository = LocalRepository()
    private val remoteRepository = RemoteRepository()

    override fun login(userRequest: UserRequest): Single<LoginResponse> =
            remoteRepository.login(userRequest).doOnSuccess {
                localRepository.saveAccessToken(it.data.token)
                localRepository.saveInfoUser(it.data.user.userName, it.data.user.fullName)
            }

    override fun hasAccessToken(): Boolean = localRepository.hasAccessToken()

    override fun getTaiSan(): Single<TaiSanResponse> = remoteRepository.getTaiSan()

    override fun getFullName(): String = localRepository.getFullName()
}
