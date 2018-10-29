package com.example.thanhhoang.qlcosovatchat.api

import com.example.thanhhoang.qlcosovatchat.data.model.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.response.LoginResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    fun login(@Body userRequest: UserRequest): Single<LoginResponse>

    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://localhost:5070")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}