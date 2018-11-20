package com.example.thanhhoang.qlcosovatchat.data.source.datasource

import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.response.KeHoachResponse
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.data.response.YeuCauResponse
import io.reactivex.Single

interface RemoteDataSource {
    fun login(userRequest: UserRequest): Single<LoginResponse>

    fun searchTaiSan(state: String?, maDinhDanh: String?): Single<TaiSanResponse>

    fun getTaiSan(): Single<TaiSanResponse>

    fun changeStatusTaiSan(equipmentId: EquipmentId): Single<TaiSanResponse>

    fun getAllTaiSanHuHong(): Single<TaiSanResponse>

    fun getAllYeuCau(): Single<YeuCauResponse>

    fun searchYeuCau(state: Int?, tieuDe: String?): Single<YeuCauResponse>

    fun delateYeuCau(id: String): Single<YeuCauResponse>

    fun getAllKeHoach(): Single<KeHoachResponse>
}
