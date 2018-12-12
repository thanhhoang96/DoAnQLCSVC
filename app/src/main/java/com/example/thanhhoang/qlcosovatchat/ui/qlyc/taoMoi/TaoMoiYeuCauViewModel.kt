package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.response.*
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class TaoMoiYeuCauViewModel(private val repository: Repository) {
    fun getAllTaiSanHuHong(): Single<TaiSanHuHongResponse> = repository.getAllTaiSanHuHong()
    fun createYeuCauSuaChua(yeuCauSuaChuaRequest: YeuCauSuaChuaRequest): Single<YeuCauResponse> = repository.createYeuCauSuaChua(yeuCauSuaChuaRequest)
    fun getPlanTypeForYeuCauMuaSam(): Single<PlanForYeuCauResponse> = repository.getPlanTypeForYeuCauMuaSam()
    fun getDetailPlanForYcms(id: String): Single<YeuCauPlanDetailResponse> = repository.getDetailPlanForYcms(id)
    fun createYeuCauMuaSam(yeuCauMuaSamRequest: YeuCauMuaSamRequest): Single<YeuCauResponse> = repository.createYeuCauMuaSam(yeuCauMuaSamRequest)
}
