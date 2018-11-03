package com.example.thanhhoang.qlcosovatchat.data.source.datasource

interface LocalDataSource {

    fun isValidToken(token: String): Boolean

    fun hasAccessToken(): Boolean

    fun getFullName(): String

    fun getAccessToken(): String
}
