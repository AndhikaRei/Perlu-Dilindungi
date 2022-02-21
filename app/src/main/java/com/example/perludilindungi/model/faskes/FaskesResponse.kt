package com.example.perludilindungi.model.faskes

import com.example.perludilindungi.model.news.News
import com.google.gson.annotations.SerializedName

data class FaskesResponse (
    @field:SerializedName("success")
    val success: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = "",

    @field:SerializedName("count_total")
    val count_total: Int? = 0,

    @field:SerializedName("results")
    val result: List<Faskes> = ArrayList<Faskes>()
)