package com.example.perludilindungi.model.news

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @field:SerializedName("success")
    val success: Boolean = false,

    @field:SerializedName("message")
    val message: String = "",

    @field:SerializedName("count_total")
    val count_total: Int = 0,

    @field:SerializedName("results")
    val result: List<News> = ArrayList<News>()
)

