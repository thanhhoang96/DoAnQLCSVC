package com.example.thanhhoang.qlcosovatchat.data.source.datasource

interface LocalDataSource {
    fun hasAccessToken(): Boolean
    fun getFullName(): String
}
