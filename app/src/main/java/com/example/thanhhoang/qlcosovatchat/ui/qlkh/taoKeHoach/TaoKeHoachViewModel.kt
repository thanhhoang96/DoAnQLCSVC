package com.example.thanhhoang.qlcosovatchat.ui.qlkh.taoKeHoach

import com.example.thanhhoang.qlcosovatchat.data.response.LoaiKeHoachResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class TaoKeHoachViewModel(private var repository: Repository) {
    fun getLoaiKeHoach(): Single<LoaiKeHoachResponse> = repository.getLoaiKeHoach()
}