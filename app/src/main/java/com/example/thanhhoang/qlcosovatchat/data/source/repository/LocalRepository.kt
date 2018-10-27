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
    }

    private val pref by lazy {
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    override fun isValidToken(token: String): Boolean = token == getAccessToken()

    private fun getAccessToken() = pref.getString(KEY_TOKEN, "")

    internal fun saveAccessToken(token: String) = pref.edit().putString(KEY_TOKEN, token).apply()

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun hasAccessToken() = getAccessToken().isNotEmpty()

}