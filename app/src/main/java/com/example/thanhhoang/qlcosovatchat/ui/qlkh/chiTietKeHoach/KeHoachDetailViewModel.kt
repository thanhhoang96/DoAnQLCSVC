package com.example.thanhhoang.qlcosovatchat.ui.qlkh.chiTietKeHoach

import com.example.thanhhoang.qlcosovatchat.data.response.KeHoachDetailResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class KeHoachDetailViewModel(private val repository: Repository) {
    fun getKeHoachDetail(id: String): Single<KeHoachDetailResponse> = repository.getKeHoachDetail(id)
}
