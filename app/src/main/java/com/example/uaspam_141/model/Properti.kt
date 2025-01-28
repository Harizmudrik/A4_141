package com.example.uaspam_141.model

import kotlinx.serialization.Serializable

@Serializable
data class Properti(
    val id_properti: String,
    val nama_properti: String,
    val deskripsi_properti: String,
    val lokasi: String,
    val harga: Double,
    val status_properti: String,

    val id_jenis: String,
    val id_pemilik: String,
    val id_manajer: String,


)
