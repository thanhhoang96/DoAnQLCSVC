package com.example.thanhhoang.qlcosovatchat.data.model.yeucau

import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.ItemKhDetail
import com.google.gson.annotations.SerializedName

data class YeuCauPlanDetailData (
        @SerializedName("planItems") var listPlanDetailForYc: MutableList<ItemKhDetail>
)
