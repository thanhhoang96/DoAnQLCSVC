package com.example.thanhhoang.qlcosovatchat.data.model.loaikehoach

import com.google.gson.annotations.SerializedName

data class LoaiKeHoachData(@SerializedName("planTypes") var loaiKeHoachList: MutableList<ItemLoaiKeHoach>)
