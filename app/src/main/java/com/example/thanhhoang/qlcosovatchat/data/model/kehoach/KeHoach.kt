package com.example.thanhhoang.qlcosovatchat.data.model.kehoach

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KeHoach(
        @SerializedName("_id") var id: String,
        @SerializedName("title") var tieuDeKeHoach: String,
        @SerializedName("planStatus") var trangThaiKeHoach: Int,
        @SerializedName("createdAt") var ngayTaoKeHoach: String,
        @SerializedName("type") var loaiKeHoach: LoaiKeHoach?
): Serializable
