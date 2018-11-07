package com.example.thanhhoang.qlcosovatchat.data.source.datasource

import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import io.reactivex.Single

interface RemoteDataSource {
    fun login(userRequest: UserRequest): Single<LoginResponse>

    fun searchTaiSan(state: String?, maDinhDanh: String?): Single<TaiSanResponse>

    fun getTaiSan(): Single<TaiSanResponse>

    fun changeStatusTaiSan(equipmentId: EquipmentId): Single<TaiSanResponse>
}
