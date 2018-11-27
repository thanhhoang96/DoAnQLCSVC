package com.example.thanhhoang.qlcosovatchat.data.model.kehoach

import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.DonVi
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.LoaiYeuCau
import com.google.gson.annotations.SerializedName

data class KeHoachDetail(
        @SerializedName("planStatus") var trangThaiKhDetail: Int,
        @SerializedName("planItems") var itemKhList: MutableList<ItemKhDetail>,
        @SerializedName("createdAt") var ngayTaoKh: String,
        var title: String,
        var desc: String,
        var type: LoaiYeuCau,
        val unit: DonVi
)
