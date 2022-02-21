package com.example.perludilindungi.model.checkin

import com.google.gson.annotations.SerializedName

data class CheckInResponse (
    @field:SerializedName("success")
    val success: String = "",

    @field:SerializedName("code")
    val code: Int = 0,

    @field:SerializedName("message")
    val message: Int = 0,

    @field:SerializedName("data")
    val data: CheckInResponseData? = CheckInResponseData()
)