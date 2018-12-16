package com.example.thanhhoang.qlcosovatchat.ui.qlyc.suaChuaYeuCau

import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauDetailResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class SuaChuaYeuCauViewModel(private var repository: Repository) {
    fun getYeuCauDetail(id: String): Single<YeuCauDetailResponse> = repository.getYeuCauDetail(id)
}