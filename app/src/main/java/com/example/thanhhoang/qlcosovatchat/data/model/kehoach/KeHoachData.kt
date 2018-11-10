package com.example.thanhhoang.qlcosovatchat.data.model.kehoach

import com.google.gson.annotations.SerializedName

data class KeHoachData(@SerializedName("plans") var keHoachList: MutableList<KeHoach>)