package com.example.perludilindungi.network

import com.example.perludilindungi.model.checkin.CheckInResponse
import com.example.perludilindungi.model.city.CityResponse
import com.example.perludilindungi.model.faskes.FaskesResponse
import com.example.perludilindungi.model.news.NewsResponse
import com.example.perludilindungi.model.province.ProvinceResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("get-news")
    fun getListNews(): Call<NewsResponse>

    @GET("get-province")
    fun getListProvince(): Call<ProvinceResponse>

    @GET("get-city")
    fun getListCity(): Call<CityResponse>

    @GET("get-faskes-vaksinasi")
    fun getFaskesVaksinasi(
       @Query("city") city: String,
       @Query("province") province: String
    ): Call<FaskesResponse>

    @FormUrlEncoded
    @POST("check-in")
    fun checkIn(
        @Field("qrCode") qrCode : String,
        @Field("latitude") latitude : String,
        @Field("longitude") longitude : String,
    ): Call<CheckInResponse>

}