package com.example.perludilindungi.model.checkin

import com.google.gson.annotations.SerializedName

data class CheckInResponseData (
    @field:SerializedName("userStatus")
    val userStatus: String = "",

    @field:SerializedName("reason")
    val reason: String = ""
)