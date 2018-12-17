package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.google.gson.annotations.SerializedName

data class ItemSuaChuaRequest (
        @SerializedName("_id") var equipmentSuaChuaId: String,
        @SerializedName("quantity") var soLuong: Int?
)
