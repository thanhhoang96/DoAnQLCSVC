package com.example.thanhhoang.qlcosovatchat.ui.qlyc.taoMoi

import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.response.PlanForYeuCauResponse
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanHuHongResponse
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauPlanDetailResponse
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class TaoMoiYeuCauViewModel(private val repository: Repository) {
    fun getAllTaiSanHuHong(): Single<TaiSanHuHongResponse> = repository.getAllTaiSanHuHong()
    fun createYeuCauSuaChua(yeuCauSuaChuaRequest: YeuCauSuaChuaRequest): Single<YeuCauResponse> = repository.createYeuCauSuaChua(yeuCauSuaChuaRequest)
    fun getPlanTypeForYeuCauMuaSam(): Single<PlanForYeuCauResponse> = repository.getPlanTypeForYeuCauMuaSam()
    fun getDetailPlanForYcms(id: String): Single<YeuCauPlanDetailResponse> = repository.getDetailPlanForYcms(id)
}
