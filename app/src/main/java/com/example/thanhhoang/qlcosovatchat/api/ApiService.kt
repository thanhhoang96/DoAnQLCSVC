package com.example.thanhhoang.qlcosovatchat.api

import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import com.example.thanhhoang.qlcosovatchat.data.response.TaiSanResponse
import com.example.thanhhoang.qlcosovatchat.util.Pref
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/login/")
    fun login(@Body userRequest: UserRequest): Single<LoginResponse>

    @GET("/asset-management")
    fun getTaiSan(): Single<TaiSanResponse>

    companion object Factory {
        fun create(): ApiService {
            val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${Pref.accessToken}")
                        .build()
                chain.proceed(newRequest)
            }.build()

            val retrofit = Retrofit.Builder()
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.1.15:5070/")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
