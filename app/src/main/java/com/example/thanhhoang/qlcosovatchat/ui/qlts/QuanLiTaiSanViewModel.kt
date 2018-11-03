package com.example.thanhhoang.qlcosovatchat.ui.qlts

import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class QuanLiTaiSanViewModel(private val repository: Repository) {
    fun taiSanList(authHeader: String): Single<TaiSanResponse> = repository.getTaiSan(authHeader)
}
