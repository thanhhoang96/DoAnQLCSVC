package com.example.thanhhoang.qlcosovatchat.data.model.taisan

import com.google.gson.annotations.SerializedName

data class Infra(
        @SerializedName("identifierNo") val maDinhDanh: String,
        @SerializedName("_id") val id: String,
        val equipment: Equipment,
        var unitEquipmentState: String,
        val groupEquipment: GroupEquipment
)