package com.example.perludilindungi.model.checkin

import com.google.gson.annotations.SerializedName

data class CheckInRequest (
    @field:SerializedName("qrCode")
    val key: String = "",

    @field:SerializedName("latitude")
    val latitude: Int = 0,

    @field:SerializedName("longitude")
    val longitude: Int = 0
)