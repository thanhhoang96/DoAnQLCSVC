package com.example.thanhhoang.qlcosovatchat.data.model

import com.google.gson.annotations.SerializedName

class User(
        @SerializedName("_id") val id: String,
        @SerializedName("username") val userName: String,
        @SerializedName("password") val passWord: String,
        @SerializedName("fullname") val fullName: String
)
