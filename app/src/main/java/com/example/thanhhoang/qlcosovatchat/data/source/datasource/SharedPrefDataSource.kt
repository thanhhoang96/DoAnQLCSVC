package com.example.thanhhoang.qlcosovatchat.data.source.datasource

/**
 * Copyright © AsianTech Co., Ltd
 * Created by at-hoaiphan on 10/1/18.
 */
interface SharedPrefDataSource {
    fun saveAccessToken(token: String)
    fun saveLoginInfo(username: String, fullname: String)
}
