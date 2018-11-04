package com.example.thanhhoang.qlcosovatchat.data.source.repository

import com.example.thanhhoang.qlcosovatchat.data.source.datasource.SharedPrefDataSource
import com.example.thanhhoang.qlcosovatchat.util.Pref


/**
 * Copyright © AsianTech Co., Ltd
 * Created by at-hoaiphan on 10/8/18.
 */
class SharedPrefDataSourceImpl : SharedPrefDataSource {
    override fun saveAccessToken(token: String) {
        Pref.accessToken = token
    }

    override fun saveLoginInfo(username: String, fullname: String) {
        Pref.username = username
        Pref.fullname = fullname
    }
}
