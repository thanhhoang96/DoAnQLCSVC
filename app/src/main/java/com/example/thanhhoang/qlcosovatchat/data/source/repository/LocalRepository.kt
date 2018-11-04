package com.example.thanhhoang.qlcosovatchat.data.source.repository

import com.example.thanhhoang.qlcosovatchat.data.source.datasource.LocalDataSource
import com.example.thanhhoang.qlcosovatchat.util.Pref

/**
 * Asian Tech Co., Ltd.
 * Created by at-nhanphan on 10/27/18
 */
class LocalRepository : LocalDataSource {

    private val shared = SharedPrefDataSourceImpl()

    override fun isValidToken(token: String): Boolean = token == getAccessToken()

    internal fun saveAccessToken(token: String) = shared.saveAccessToken(token)

    internal fun saveInfoUser(userName: String, fullName: String) {
        shared.saveLoginInfo(userName, fullName)
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun hasAccessToken() = getAccessToken().isNotEmpty()

    override fun getFullName() = Pref.fullname

    override fun getAccessToken() = Pref.accessToken

    fun getUsername() = Pref.username
}
