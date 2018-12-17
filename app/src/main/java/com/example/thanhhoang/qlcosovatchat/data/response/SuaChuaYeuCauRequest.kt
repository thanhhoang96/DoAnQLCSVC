package com.example.thanhhoang.qlcosovatchat.data.response

import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemSuaChuaRequest

data class SuaChuaYeuCauRequest(
        var title: String,
        var desc: String?,
        var proposalItems: MutableList<ItemSuaChuaRequest>
)
