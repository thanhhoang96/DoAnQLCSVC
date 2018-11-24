package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.google.gson.annotations.SerializedName

data class YeuCauSuaChuaRequest(
        @SerializedName("desc") val moTaYeuCau: String?,
        @SerializedName("title") val tieuDeYeuCau: String,
        @SerializedName("unitEquipmentIsChecked") val mangIdChecked: MutableList<String>?
)
