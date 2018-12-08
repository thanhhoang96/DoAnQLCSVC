package com.example.thanhhoang.qlcosovatchat.data.source.repository

import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.Plans
import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.response.*
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.LocalDataSource
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class Repository : LocalDataSource, RemoteDataSource {

    private val localRepository = LocalRepository()
    private val remoteRepository = RemoteRepository()

    override fun login(userRequest: UserRequest): Single<LoginResponse> =
            remoteRepository.login(userRequest).doOnSuccess {
                localRepository.saveAccessToken(it.data.token)
                localRepository.saveInfoUser(it.data.user.userName, it.data.user.fullName)
            }

    override fun getTaiSan(): Single<TaiSanResponse> = remoteRepository.getTaiSan()

    override fun searchTaiSan(state: String?, maDinhDanh: String?): Single<TaiSanResponse> = remoteRepository.searchTaiSan(state, maDinhDanh)

    override fun changeStatusTaiSan(equipmentId: EquipmentId): Single<TaiSanResponse> = remoteRepository.changeStatusTaiSan(equipmentId)

    override fun getAllTaiSanHuHong(): Single<TaiSanHuHongResponse> = remoteRepository.getAllTaiSanHuHong()

    override fun hasAccessToken(): Boolean = localRepository.hasAccessToken()

    override fun getFullName(): String = localRepository.getFullName()

    override fun getAllYeuCau(): Single<YeuCauResponse> = remoteRepository.getAllYeuCau()

    override fun searchYeuCau(state: Int?, tieuDe: String?): Single<YeuCauResponse> = remoteRepository.searchYeuCau(state, tieuDe)

    override fun getYeuCauDetail(id: String): Single<YeuCauDetailResponse> = remoteRepository.getYeuCauDetail(id)

    override fun createYeuCauSuaChua(yeuCauSuaChuaRequest: YeuCauSuaChuaRequest): Single<YeuCauResponse> = remoteRepository.createYeuCauSuaChua(yeuCauSuaChuaRequest)

    override fun deleteYeuCau(id: String): Single<YeuCauResponse> = remoteRepository.deleteYeuCau(id)

    override fun getAllKeHoach(): Single<KeHoachResponse> = remoteRepository.getAllKeHoach()

    override fun searchKeHoach(trangThai: Int?, tenKeHoach: String?): Single<KeHoachResponse> = remoteRepository.searchKeHoach(trangThai, tenKeHoach)

    override fun getKeHoachDetail(id: String): Single<KeHoachDetailResponse> = remoteRepository.getKeHoachDetail(id)

    override fun getLoaiKeHoach(): Single<LoaiKeHoachResponse> = remoteRepository.getLoaiKeHoach()

    override fun getNhomThietBi(): Single<NhomThietBiResponse> = remoteRepository.getNhomThietBi()

    override fun getThietBi(idGroupThietBi: String): Single<ThietBiResponse> = remoteRepository.getThietBi(idGroupThietBi)

    override fun createNewKeHoach(plans: Plans): Single<KeHoachResponse> = remoteRepository.createNewKeHoach(plans)
}
