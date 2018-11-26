package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Equipment
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.GroupEquipment
import com.google.gson.annotations.SerializedName

data class ItemYcDetail(
        @SerializedName("identifierNo") var maDinhDanh: String,
        var equipment: Equipment,
        var groupEquipment: GroupEquipment
)
