package com.example.perludilindungi.model.faskes

import com.example.perludilindungi.model.province.Province
import com.google.gson.annotations.SerializedName

data class Faskes (
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("kode")
    val kode: String = "",

    @field:SerializedName("nama")
    val nama: String = "",

    @field:SerializedName("kota")
    val kota: String = "",

    @field:SerializedName("provinsi")
    val provinsi: String = "",

    @field:SerializedName("alamat")
    val alamat: String = "",

    @field:SerializedName("latitude")
    val latitude: String = "",

    @field:SerializedName("longitude")
    val longitude: String = "",

    @field:SerializedName("telp")
    val telp: String = "",

    @field:SerializedName("jenis_faskes")
    val jenis_faskes: String = "",

    @field:SerializedName("kelas_rs")
    val kelas_rs: String = "",

    @field:SerializedName("status")
    val status: String = "",

    @field:SerializedName("detail")
    val detail: List<FaskesDetail> = ArrayList(),

    @field:SerializedName("source_data")
    val source_data: String = ""
)