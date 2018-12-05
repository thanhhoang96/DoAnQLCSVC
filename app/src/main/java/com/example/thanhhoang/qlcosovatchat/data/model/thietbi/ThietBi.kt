package com.example.thanhhoang.qlcosovatchat.data.model.thietbi

import com.google.gson.annotations.SerializedName

data class ThietBi(
        @SerializedName("_id") var idThietBi: String,
        var name: String
)
