package com.example.thanhhoang.qlcosovatchat.data.model.taisan

import com.google.gson.annotations.SerializedName

data class Equipment(
        var name: String,
        var nameNo: String,
        var measureUnit: MeasureUnit,
        var equipmentGroup: GroupEquipment,
        @SerializedName("unitPrice") var donGia: Int?
)
