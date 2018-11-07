package com.example.thanhhoang.qlcosovatchat.data.source.repository

import com.example.thanhhoang.qlcosovatchat.api.ApiService
import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.data.source.datasource.RemoteDataSource
import io.reactivex.Single

class RemoteRepository : RemoteDataSource {

    private val apiService = ApiService.create()

    override fun login(userRequest: UserRequest): Single<LoginResponse> = apiService.login(userRequest)

    override fun getTaiSan(): Single<TaiSanResponse> = apiService.getTaiSan()

    override fun searchTaiSan(state: String?, maDinhDanh: String?): Single<TaiSanResponse> = apiService.searchTaiSan(state, maDinhDanh)

    override fun changeStatusTaiSan(equipmentId: EquipmentId): Single<TaiSanResponse> = apiService.changeStatusTaiSan(equipmentId)
}
