package com.example.thanhhoang.qlcosovatchat.api

import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.Equipment
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.util.Pref
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST("/login")
    fun login(@Body userRequest: UserRequest): Single<LoginResponse>

    @GET("/asset-management")
    fun getTaiSan(): Single<TaiSanResponse>

    @GET("/asset-management")
    fun searchTaiSan(@Query("action") state: String?, @Query("keyword") maDinhDanh: String?): Single<TaiSanResponse>

    @PATCH("/asset-management/status")
    fun changeStatusTaiSan(@Body unitEquipmentId: EquipmentId): Single<TaiSanResponse>

    companion object Factory {
        fun create(): ApiService {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${Pref.accessToken}")
                        .build()
                chain.proceed(newRequest)
            }

            HttpLoggingInterceptor().let {
                it.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(it)
            }

            val retrofit = Retrofit.Builder()
                    .client(builder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.1.15:5070")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
