package com.example.thanhhoang.qlcosovatchat.data.model.taisan

import com.google.gson.annotations.SerializedName

data class TaiSanData(@SerializedName("infrastructures")var taiSanList: MutableList<Infra>)
