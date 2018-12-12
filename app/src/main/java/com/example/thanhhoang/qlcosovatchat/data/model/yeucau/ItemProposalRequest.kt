package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.google.gson.annotations.SerializedName

data class ItemProposalRequest(
        @SerializedName("_id") var equipmentId: String,
        @SerializedName("plan") var planId: String,
        @SerializedName("quantity") var soLuong: Int
)
