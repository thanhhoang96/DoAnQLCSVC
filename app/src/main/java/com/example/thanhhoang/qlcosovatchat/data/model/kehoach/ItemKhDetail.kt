package com.example.thanhhoang.qlcosovatchat.data.model.kehoach

import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Equipment
import com.google.gson.annotations.SerializedName

class ItemKhDetail(
        @SerializedName("_id") var detailPlanId: String,
        @SerializedName("quantity") var soLuongDeNghi: Int,
        var equipment: Equipment,
        @SerializedName("approvedQuantity") var soLuongPheDuyet: Int,
        @SerializedName("remainQuantity") var soLuongConLai: Int,
        @SerializedName("proposalQuantity") var soLuongDangYeuCau: Int
)
