package com.example.thanhhoang.qlcosovatchat.data.source.datasource

import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.Plans
import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.response.*
import io.reactivex.Single

interface RemoteDataSource {
    fun login(userRequest: UserRequest): Single<LoginResponse>

    fun searchTaiSan(state: String?, maDinhDanh: String?): Single<TaiSanResponse>

    fun getTaiSan(): Single<TaiSanResponse>

    fun changeStatusTaiSan(equipmentId: EquipmentId): Single<TaiSanResponse>

    fun getAllTaiSanHuHong(): Single<TaiSanHuHongResponse>

    fun getAllYeuCau(): Single<YeuCauResponse>

    fun searchYeuCau(state: Int?, tieuDe: String?): Single<YeuCauResponse>

    fun getYeuCauDetail(id: String): Single<YeuCauDetailResponse>

    fun createYeuCauSuaChua(yeuCauSuaChuaRequest: YeuCauSuaChuaRequest): Single<YeuCauResponse>

    fun deleteYeuCau(id: String): Single<YeuCauResponse>

    fun getPlanTypeForYeuCauMuaSam(): Single<PlanForYeuCauResponse>

    fun getDetailPlanForYcms(id: String): Single<YeuCauPlanDetailResponse>

    fun createYeuCauMuaSam(yeuCauMuaSamRequest: YeuCauMuaSamRequest): Single<YeuCauResponse>

    fun getAllKeHoach(): Single<KeHoachResponse>

    fun searchKeHoach(trangThai: Int?, tenKeHoach: String?): Single<KeHoachResponse>

    fun getKeHoachDetail(id: String): Single<KeHoachDetailResponse>

    fun getLoaiKeHoach(): Single<LoaiKeHoachResponse>

    fun getNhomThietBi(): Single<NhomThietBiResponse>

    fun getThietBi(idGroupThietBi: String): Single<ThietBiResponse>

    fun createNewKeHoach(plan: Plans): Single<KeHoachResponse>

    fun deletePlan(id: String): Single<KeHoachResponse>

    fun repairKeHoach(planId: String, plan: Plans): Single<KeHoachResponse>
}
