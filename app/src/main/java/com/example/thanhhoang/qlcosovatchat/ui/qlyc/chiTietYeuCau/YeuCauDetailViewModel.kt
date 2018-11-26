package com.example.thanhhoang.qlcosovatchat.ui.qlyc.chiTietYeuCau

import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauDetailResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class YeuCauDetailViewModel(private val repository: Repository) {
    fun getYeuCauDetail(id: String): Single<YeuCauDetailResponse> = repository.getYeuCauDetail(id)
}
