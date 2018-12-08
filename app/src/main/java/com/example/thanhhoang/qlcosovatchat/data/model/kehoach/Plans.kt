package com.example.thanhhoang.qlcosovatchat.data.model.kehoach

import android.content.ClipData

data class Plans (
        var title: String,
        var desc: String?,
        var type: String,
        var planItems: MutableList<EquipmentItem>
)
