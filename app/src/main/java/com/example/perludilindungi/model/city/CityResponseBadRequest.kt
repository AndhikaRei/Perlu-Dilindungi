package com.example.perludilindungi.model.city

import com.google.gson.annotations.SerializedName

data class CityResponseBadRequest (
    @field:SerializedName("curr_val")
    val curr_val: String = "",

    @field:SerializedName("message")
    val message: String = ""
)