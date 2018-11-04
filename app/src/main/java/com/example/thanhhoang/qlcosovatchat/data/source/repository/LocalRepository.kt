package com.example.thanhhoang.qlcosovatchat.data.source.repository

import com.example.thanhhoang.qlcosovatchat.data.source.datasource.LocalDataSource
import com.example.thanhhoang.qlcosovatchat.util.Pref

/**
 * Asian Tech Co., Ltd.
 * Created by at-nhanphan on 10/27/18
 */
class LocalRepository : LocalDataSource {

    private val shared = SharedPrefDataSourceImpl()

    internal fun saveAccessToken(token: String) = shared.saveAccessToken(token)

    internal fun saveInfoUser(userName: String, fullName: String) {
        shared.saveLoginInfo(userName, fullName)
    }

    override fun hasAccessToken() = getAccessToken().isNotEmpty()

    override fun getFullName() = Pref.fullname

    private fun getAccessToken() = Pref.accessToken
}
