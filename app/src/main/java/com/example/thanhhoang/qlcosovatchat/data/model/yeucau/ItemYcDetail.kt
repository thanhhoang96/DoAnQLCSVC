package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Equipment
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.GroupEquipment
import com.google.gson.annotations.SerializedName

data class ItemYcDetail(
        @SerializedName("_id") var itemProposalId: String,
        @SerializedName("quantity") var soLuongYeuCau: Int,
        @SerializedName("approvedQuantity") var soLuongDuyet: Int,
        @SerializedName("identifierNo") var maDinhDanh: String,
        @SerializedName("planItem") var planItem: PlanItem?,
        var equipment: Equipment?,
        var groupEquipment: GroupEquipment?
)
