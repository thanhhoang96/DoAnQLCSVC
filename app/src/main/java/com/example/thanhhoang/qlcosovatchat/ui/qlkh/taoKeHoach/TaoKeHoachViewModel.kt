package com.example.thanhhoang.qlcosovatchat.ui.qlkh.taoKeHoach

import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.Plans
import com.example.thanhhoang.qlcosovatchat.data.response.*
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class TaoKeHoachViewModel(private var repository: Repository) {
    fun getLoaiKeHoach(): Single<LoaiKeHoachResponse> = repository.getLoaiKeHoach()

    fun getNhomThietBi(): Single<NhomThietBiResponse> = repository.getNhomThietBi()

    fun getThietBi(idGroupThietBi: String): Single<ThietBiResponse> = repository.getThietBi(idGroupThietBi)

    fun createNewKeHoach(plans: Plans): Single<KeHoachResponse> = repository.createNewKeHoach(plans)

    fun getKeHoachDetail(id: String): Single<KeHoachDetailResponse> = repository.getKeHoachDetail(id)

    fun repairKeHoach(planId: String, plan: Plans): Single<KeHoachResponse> = repository.repairKeHoach(planId, plan)
}