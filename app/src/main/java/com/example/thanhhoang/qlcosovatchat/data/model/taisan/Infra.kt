package com.example.thanhhoang.qlcosovatchat.data.model.taisan

import com.google.gson.annotations.SerializedName

data class Infra(
        @SerializedName("indentifierNo") val maDinhDanh: String,
        val equipment: Equipment,
        var unitEquipmentState: String,
        val groupEquipment: GroupEquipment
)