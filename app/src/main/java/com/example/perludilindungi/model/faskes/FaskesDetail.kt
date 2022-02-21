package com.example.perludilindungi.model.faskes

import com.google.gson.annotations.SerializedName

class FaskesDetail (
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("kode")
    val kode: String = "",

    @field:SerializedName("batch")
    val batch: String = "",

    @field:SerializedName("divaksin")
    val divaksin: Int = 0,

    @field:SerializedName("divaksin_1")
    val divaksin_1: Int = 0,

    @field:SerializedName("divaksin_2")
    val divaksin_2: Int = 0,

    @field:SerializedName("batal_vaksin")
    val batal_vaksin: Int = 0,

    @field:SerializedName("batal_vaksin_1")
    val batal_vaksin_1: Int = 0,

    @field:SerializedName("batal_vaksin_2")
    val batal_vaksin_2: Int = 0,

    @field:SerializedName("pending_vaksin")
    val pending_vaksin: Int = 0,

    @field:SerializedName("pending_vaksin_1")
    val pending_vaksin_1: Int = 0,

    @field:SerializedName("pending_vaksin_2")
    val pending_vaksin_2: Int = 0,

    @field:SerializedName("tanggal")
    val tanggal: String = ""
)