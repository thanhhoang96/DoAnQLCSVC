package com.example.thanhhoang.qlcosovatchat.util

import com.chibatching.kotpref.KotprefModel

/**
 * Copyright © AsianTech Co., Ltd
 * Created by at-hoaiphan on 9/12/18.
 */
object Pref : KotprefModel() {

    var accessToken by stringPref()

    var username by stringPref()
    var fullname by stringPref()
}
