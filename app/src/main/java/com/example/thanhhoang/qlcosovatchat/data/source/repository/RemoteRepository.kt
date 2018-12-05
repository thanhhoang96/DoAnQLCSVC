package com.example.thanhhoang.qlcosovatchat.data.source.repository

import com.example.thanhhoang.qlcosovatchat.api.ApiService
import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.response.*
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class RemoteRepository : RemoteDataSource {

    private val apiService = ApiService.create()

    override fun login(userRequest: UserRequest): Single<LoginResponse> = apiService.login(userRequest)

    override fun getTaiSan(): Single<TaiSanResponse> = apiService.getTaiSan()

    override fun searchTaiSan(state: String?, maDinhDanh: String?): Single<TaiSanResponse> = apiService.searchTaiSan(state, maDinhDanh)

    override fun changeStatusTaiSan(equipmentId: EquipmentId): Single<TaiSanResponse> = apiService.changeStatusTaiSan(equipmentId)

    override fun getAllTaiSanHuHong(): Single<TaiSanHuHongResponse> = apiService.getAllTaiSanHuHong()

    override fun getAllYeuCau(): Single<YeuCauResponse> = apiService.getAllYeuCau()

    override fun searchYeuCau(state: Int?, tieuDe: String?): Single<YeuCauResponse> = apiService.searchYeuCau(state, tieuDe)

    override fun getYeuCauDetail(id: String): Single<YeuCauDetailResponse> = apiService.getYeuCauDetail(id)

    override fun createYeuCauSuaChua(yeuCauSuaChuaRequest: YeuCauSuaChuaRequest): Single<YeuCauResponse> = apiService.createYeuCauSuaChua(yeuCauSuaChuaRequest)

    override fun deleteYeuCau(id: String): Single<YeuCauResponse> = apiService.deleteYeuCau(id)

    override fun getAllKeHoach(): Single<KeHoachResponse> = apiService.getAllKeHoach()

    override fun searchKeHoach(trangThai: Int?, tenKeHoach: String?): Single<KeHoachResponse> = apiService.searchKeHoach(trangThai, tenKeHoach)

    override fun getKeHoachDetail(id: String): Single<KeHoachDetailResponse> = apiService.getKeHoachDetail(id)

    override fun getLoaiKeHoach(): Single<LoaiKeHoachResponse> = apiService.getLoaiKeHoach()

    override fun getNhomThietBi(): Single<NhomThietBiResponse> = apiService.getNhomThietBi()

    override fun getThietBi(idGroupThietBi: String): Single<ThietBiResponse> = apiService.getThietBi(idGroupThietBi)
}
