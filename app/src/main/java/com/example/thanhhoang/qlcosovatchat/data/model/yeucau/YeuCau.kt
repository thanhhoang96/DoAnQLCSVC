package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.google.gson.annotations.SerializedName

data class YeuCau(
        @SerializedName("_id") var idYC: String,
        @SerializedName("title") var tieuDeYC: String,
        @SerializedName("proposalStatus") var trangThaiYC: Int,
        @SerializedName("createdAt") var ngayTaoYC: String
)
