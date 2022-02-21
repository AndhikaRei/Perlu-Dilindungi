package com.example.perludilindungi.model.city

import com.example.perludilindungi.model.province.Province
import com.google.gson.annotations.SerializedName

data class CityResponse (
    @field:SerializedName("curr_val")
    val curr_val: String = "",

    @field:SerializedName("message")
    val message: String = "",

    @field:SerializedName("results")
    val results: List<Province> = ArrayList()
)