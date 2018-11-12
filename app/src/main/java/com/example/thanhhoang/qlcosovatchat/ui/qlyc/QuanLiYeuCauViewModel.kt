package com.example.thanhhoang.qlcosovatchat.ui.qlyc

import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class QuanLiYeuCauViewModel(private val repository: Repository) {
    fun getAllYeuCau(): Single<YeuCauResponse> = repository.getAllYeuCau()
    fun searchYeuCau(state: Int?, tieuDe: String?): Single<YeuCauResponse> = repository.searchYeuCau(state, tieuDe)
}