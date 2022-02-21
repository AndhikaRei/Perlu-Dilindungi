package com.example.perludilindungi.model.news

import com.google.gson.annotations.SerializedName

data class News (
    @field:SerializedName("title")
    val title: String = "",

    @field:SerializedName("link")
    val link: List<String> = ArrayList<String>(),

    @field:SerializedName("guid")
    val guid: String = "",

    @field:SerializedName("pubDate")
    val pubDate: String = "",

    @field:SerializedName("description")
    val description: Description = Description(),

    @field:SerializedName("enclosure")
    val enclosure: Enclosure = Enclosure(),
)

