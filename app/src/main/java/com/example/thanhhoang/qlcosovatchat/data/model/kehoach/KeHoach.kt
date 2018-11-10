package com.example.thanhhoang.qlcosovatchat.data.model.kehoach

import com.google.gson.annotations.SerializedName

data class KeHoach(
        @SerializedName("title") var tieuDeKeHoach: String,
        @SerializedName("planStatus") var trangThaiKeHoach: Int,
        @SerializedName("createdAt") var ngayTaoKeHoach: String,
        @SerializedName("type") var loaiKeHoach: LoaiKeHoach
)