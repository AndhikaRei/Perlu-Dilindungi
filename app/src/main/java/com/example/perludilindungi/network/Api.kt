package com.example.perludilindungi.network

import com.example.perludilindungi.model.checkin.CheckInResponse
import com.example.perludilindungi.model.city.CityResponse
import com.example.perludilindungi.model.faskes.FaskesResponse
import com.example.perludilindungi.model.news.NewsResponse
import com.example.perludilindungi.model.province.ProvinceResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("api/get-news")
    fun getListNews(): Call<NewsResponse>

    @GET("api/get-province")
    fun getListProvince(): Call<ProvinceResponse>

    @GET("api/get-city")
    fun getListCity(
        @Query("start_id") start_id: String,
    ): Call<CityResponse>

    @GET("api/get-faskes-vaksinasi")
    fun getFaskesVaksinasi(
       @Query("city") city: String,
       @Query("province") province: String
    ): Call<FaskesResponse>

    @FormUrlEncoded
    @POST("check-in")
    fun checkIn(
        @Field("qrCode") qrCode : String,
        @Field("latitude") latitude : Double,
        @Field("longitude") longitude : Double,
    ): Call<CheckInResponse>

}