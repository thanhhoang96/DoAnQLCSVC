package com.example.thanhhoang.qlcosovatchat.data.model.thietbi

import com.example.thanhhoang.qlcosovatchat.data.model.taisan.GroupEquipment
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.MeasureUnit
import com.google.gson.annotations.SerializedName

data class ThietBi(
        @SerializedName("_id") var idThietBi: String,
        var name: String,
        var equipmentGroup: GroupEquipment,
        @SerializedName("measureUnit") var donVi: MeasureUnit,
        var unitPrice: Long,
        var soLuong: Int?
)
