package com.example.thanhhoang.qlcosovatchat.data.source.datasource

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

    fun createYeuCauSuaChua(yeuCauSuaChuaRequest: YeuCauSuaChuaRequest): Single<YeuCauResponse>

    fun deleteYeuCau(id: String): Single<YeuCauResponse>

    fun getAllKeHoach(): Single<KeHoachResponse>
}
