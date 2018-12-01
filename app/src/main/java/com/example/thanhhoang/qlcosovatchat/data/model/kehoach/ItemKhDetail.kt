package com.example.thanhhoang.qlcosovatchat.data.model.kehoach

import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Equipment
import com.google.gson.annotations.SerializedName

class ItemKhDetail(
        @SerializedName("quantity") var soLuongDeNghi: Int,
        var equipment: Equipment,
        @SerializedName("approvedQuantity") var soLuongPheDuyet: Int
)