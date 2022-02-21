package com.example.perludilindungi.model.news

import com.google.gson.annotations.SerializedName

data class Enclosure (
    @field:SerializedName("_url")
    val _url: String = "",

    @field:SerializedName("_length")
    val _length: String = "",

    @field:SerializedName("_type")
    val _type: String = ""
)