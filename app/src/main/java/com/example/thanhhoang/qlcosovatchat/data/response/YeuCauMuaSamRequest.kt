package com.example.thanhhoang.qlcosovatchat.data.response

import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.ItemProposalRequest

data class YeuCauMuaSamRequest(
        var title: String,
        var desc: String,
        var proposals : MutableList<ItemProposalRequest>
        )
