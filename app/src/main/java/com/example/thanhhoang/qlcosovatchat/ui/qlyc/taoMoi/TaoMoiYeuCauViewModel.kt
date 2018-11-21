package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanHuHongResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class TaoMoiYeuCauViewModel(private val repository: Repository) {
    fun getAllTaiSanHuHong(): Single<TaiSanHuHongResponse> = repository.getAllTaiSanHuHong()
}
