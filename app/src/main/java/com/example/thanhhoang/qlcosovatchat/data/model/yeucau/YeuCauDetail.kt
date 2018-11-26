package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.google.gson.annotations.SerializedName

data class YeuCauDetail(
        @SerializedName("proposalStatus") var trangThaiYcDetail: Int,
        @SerializedName("proposalItems") var listYeuCauDetail: MutableList<ItemYcDetail>,
        @SerializedName("createdAt") var ngayTaoYeuCauDetail: String,
        var title: String,
        var desc: String,
        var type: LoaiYeuCau,
        val unit: DonVi
)
