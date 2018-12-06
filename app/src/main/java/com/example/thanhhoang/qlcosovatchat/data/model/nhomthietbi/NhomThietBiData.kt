package com.example.thanhhoang.qlcosovatchat.data.model.nhomthietbi

import com.google.gson.annotations.SerializedName

data class NhomThietBiData (
        @SerializedName("equimentGroups") var listNhomThietBi: MutableList<NhomThietBi>
)
