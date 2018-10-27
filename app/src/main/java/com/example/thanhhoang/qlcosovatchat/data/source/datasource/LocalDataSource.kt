package com.example.thanhhoang.qlcosovatchat.data.source.datasource

/**
 * Asian Tech Co., Ltd.
 * Created by at-nhanphan on 10/27/18
 */
interface LocalDataSource {

    fun isValidToken(token: String): Boolean

    fun hasAccessToken(): Boolean
}