package com.example.thanhhoang.qlcosovatchat.ui.qlkh

import com.example.thanhhoang.qlcosovatchat.data.response.KeHoachResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class QuanLiKeHoachViewModel(private var repository: Repository) {
    fun getAllKeHoach(): Single<KeHoachResponse> = repository.getAllKeHoach()
    fun searchKeHoach(trangThai: Int?, tenKeHoach: String?): Single<KeHoachResponse> = repository.searchKeHoach(trangThai, tenKeHoach)
}
