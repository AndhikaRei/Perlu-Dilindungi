package com.example.perludilindungi.model.province

import com.google.gson.annotations.SerializedName

data class Province (
    @field:SerializedName("key")
    val key: String? = "",

    @field:SerializedName("value")
    val value: String? = ""
)