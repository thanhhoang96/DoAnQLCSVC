package com.example.thanhhoang.qlcosovatchat.api

import com.example.thanhhoang.qlcosovatchat.data.model.kehoach.Plans
import com.example.thanhhoang.qlcosovatchat.data.model.login.UserRequest
import com.example.thanhhoang.qlcosovatchat.data.model.taisan.EquipmentId
import com.example.thanhhoang.qlcosovatchat.data.model.yeucau.YeuCauSuaChuaRequest
import com.example.thanhhoang.qlcosovatchat.data.response.*
import com.example.thanhhoang.qlcosovatchat.util.Pref
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {
    // api login
    @POST("/login")
    fun login(@Body userRequest: UserRequest): Single<LoginResponse>

    /*
    * call api quan li tai san
    * */
    @GET("/asset-management")
    fun getTaiSan(): Single<TaiSanResponse>

    @GET("/asset-management")
    fun searchTaiSan(@Query("active") state: String?, @Query("keyword") maDinhDanh: String?): Single<TaiSanResponse>

    @PATCH("/asset-management/status")
    fun changeStatusTaiSan(@Body equipmentId: EquipmentId): Single<TaiSanResponse>

    @GET("/asset-management/damaged")
    fun getAllTaiSanHuHong(): Single<TaiSanHuHongResponse>

    /*
    * api quan li yeu cau
    * */
    @GET("/proposal")
    fun getAllYeuCau(): Single<YeuCauResponse>

    @GET("/proposal")
    fun searchYeuCau(@Query("active") state: Int?, @Query("keyword") tieuDe: String?): Single<YeuCauResponse>

    @GET("/proposal/{id}")
    fun getYeuCauDetail(@Path("id") id: String): Single<YeuCauDetailResponse>

    @POST("/proposal/prepair")
    fun createYeuCauSuaChua(@Body yeuCauSuaChuaRequest: YeuCauSuaChuaRequest): Single<YeuCauResponse>

    @DELETE("/proposal/{id}")
    fun deleteYeuCau(@Path("id") id: String): Single<YeuCauResponse>

    @GET("/plan/aprroved/2")
    fun getPlanTypeForYeuCauMuaSam(): Single<PlanForYeuCauResponse>

    @GET("/plan/filter/detail/{id}")
    fun getDetailPlanForYcms(@Path("id") id: String): Single<YeuCauPlanDetailResponse>

    @POST("/proposal/according-plan")
    fun createYeuCauMuaSam(@Body yeuCauMuaSamRequest: YeuCauMuaSamRequest): Single<YeuCauResponse>

    @PATCH("/proposal/according-plan/{id}")
    fun repairYeuCauMuaSam(@Path("id") yeuCauId: String, @Body muaSamRequest: SuaChuaYeuCauRequest): Single<YeuCauResponse>

    @PATCH("/proposal/prepair/{id}")
    fun repairYeuCauSuaChua(@Path("id") yeuCauId: String, @Body suaChuaRequest: SuaChuaYeuCauRequest): Single<YeuCauResponse>

    /*
    * api quan li ke hoach
    * */
    @GET("/plan")
    fun getAllKeHoach(): Single<KeHoachResponse>

    @GET("/plan/{id}")
    fun getKeHoachDetail(@Path("id") id: String): Single<KeHoachDetailResponse>

    @GET("/plan")
    fun searchKeHoach(@Query("active") trangThai: Int?, @Query("keyword") tenKeHoach: String?): Single<KeHoachResponse>

    @GET("/plan-type")
    fun getLoaiKeHoach(): Single<LoaiKeHoachResponse>

    @GET("/equipment-group")
    fun getNhomThietBi(): Single<NhomThietBiResponse>

    @GET("/equipment/all-by-equipment-group")
    fun getThietBi(@Query("equipmentGroup") idEquipmentGroup: String): Single<ThietBiResponse>

    @POST("/plan")
    fun createNewKeHoach(@Body plan: Plans): Single<KeHoachResponse>

    @DELETE("/plan/{id}")
    fun deletePlan(@Path("id") id: String): Single<KeHoachResponse>

    @PUT("/plan/{id}")
    fun repairKeHoach(@Path("id") planId: String, @Body plan: Plans): Single<KeHoachResponse>

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

            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                    .client(builder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://quan-ly-csvc.herokuapp.com")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
