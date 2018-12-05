package com.example.thanhhoang.qlcosovatchat.data.model.nhomthietbi

import com.google.gson.annotations.SerializedName

data class NhomThietBiData (
        @SerializedName("equipmentGroups") var listNhomThietBi: MutableList<NhomThietBi>
)
