package com.example.thanhhoang.qlcosovatchat.ui.qlyc.suaChuaYeuCau

import com.example.thanhhoang.qlcosovatchat.data.response.SuaChuaYeuCauRequest
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauDetailResponse
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauResponse
import com.example.thanhhoang.qlcosovatchat.data.source.repository.Repository
import io.reactivex.Single

class SuaChuaYeuCauViewModel(private var repository: Repository) {
    fun getYeuCauDetail(id: String): Single<YeuCauDetailResponse> = repository.getYeuCauDetail(id)
    fun repairYeuCauMuaSam(yeuCauId: String, muaSamRequest: SuaChuaYeuCauRequest): Single<YeuCauResponse> = repository.repairYeuCauMuaSam(yeuCauId, muaSamRequest)
    fun repairYeuCauSuaChua(yeuCauId: String, suaChuaRequest: SuaChuaYeuCauRequest): Single<YeuCauResponse> = repository.repairYeuCauMuaSam(yeuCauId, suaChuaRequest)
}
