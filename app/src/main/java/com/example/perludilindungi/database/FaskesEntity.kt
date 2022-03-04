package com.example.perludilindungi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.perludilindungi.model.faskes.FaskesDetail
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bookmarked_faskes")
data class FaskesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    @ColumnInfo(name = "kode")
    val kode: String = "",

    @ColumnInfo(name = "nama")
    val nama: String = "",

    @ColumnInfo(name = "kota")
    val kota: String = "",

    @ColumnInfo(name = "provinsi")
    val provinsi: String = "",

    @ColumnInfo(name = "alamat")
    val alamat: String = "",

    @ColumnInfo(name = "latitude")
    val latitude: String = "",

    @ColumnInfo(name = "longitude")
    val longitude: String = "",

    @ColumnInfo(name = "telp")
    val telp: String = "",

    @ColumnInfo(name = "jenis_faskes")
    val jenis_faskes: String = "",

    @ColumnInfo(name = "status")
    val status: String = "",
)