package com.example.perludilindungi.model.faskes

import com.google.gson.annotations.SerializedName

data class FaskesResponseBadRequest (
    @field:SerializedName("curr_val")
    val curr_val: String? = "",

    @field:SerializedName("message")
    val message: String? = ""
)