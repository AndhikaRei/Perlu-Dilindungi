package com.example.perludilindungi.model.province

import com.example.perludilindungi.model.news.News
import com.google.gson.annotations.SerializedName

data class ProvinceResponse (
    @field:SerializedName("curr_val")
    val curr_val: String? = "",

    @field:SerializedName("message")
    val message: String? = "",

    @field:SerializedName("results")
    val results: List<Province> = ArrayList()
)