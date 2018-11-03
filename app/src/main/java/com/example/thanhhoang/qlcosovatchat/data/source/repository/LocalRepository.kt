package com.example.thanhhoang.qlcosovatchat.data.source.repository

import android.content.Context
import com.example.thanhhoang.qlcosovatchat.BuildConfig
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.LocalDataSource

/**
 * Asian Tech Co., Ltd.
 * Created by at-nhanphan on 10/27/18
 */
class LocalRepository(private val context: Context) : LocalDataSource {

    companion object {
        private const val KEY_TOKEN = "key_token"
        private const val KEY_USER_NAME = "key_username"
        private const val KEY_FULL_NAME = "key_fullName"
    }

    private val pref by lazy {
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    override fun isValidToken(token: String): Boolean = token == getAccessToken()

    internal fun saveAccessToken(token: String) = pref.edit().putString(KEY_TOKEN, token).apply()

    internal fun saveInfoUser(userName: String, fullName: String) {
        pref.edit().apply {
            putString(KEY_USER_NAME, userName)
            putString(KEY_FULL_NAME, fullName)
        }.apply()
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun hasAccessToken() = getAccessToken().isNotEmpty()

    override fun getFullName() = pref.getString(KEY_FULL_NAME, "")

    override fun getAccessToken() = pref.getString(KEY_TOKEN, "")

    fun getUsername() = pref.getString(KEY_USER_NAME, "")
}
