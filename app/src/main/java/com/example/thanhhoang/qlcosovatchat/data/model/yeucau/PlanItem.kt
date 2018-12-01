package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Equipment
import com.google.gson.annotations.SerializedName

data class PlanItem(
        @SerializedName("quantity") var soLuongYeuCau: Int,
        @SerializedName("approvedQuantity") var soLuongDuyet: Int,
        var equipment: Equipment
)
