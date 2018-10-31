package com.example.thanhhoang.qlcosovatchat.data.model.login

import com.google.gson.annotations.SerializedName

data class UserRequest(
        @SerializedName("username") val userName: String,
        @SerializedName("password") val passWord: String
        )