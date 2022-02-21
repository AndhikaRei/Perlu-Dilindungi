package com.example.perludilindungi.model.city

import com.google.gson.annotations.SerializedName

data class City (
    @field:SerializedName("key")
    val key: String = "",

    @field:SerializedName("value")
    val value: String = ""
)