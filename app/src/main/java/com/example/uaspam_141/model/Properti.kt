package com.example.uaspam_141.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properti(
    @SerialName("id_properti") val idProperti: String,
    @SerialName("nama_properti") val namaProperti: String,
    @SerialName("deskripsi_properti") val deskripsiProperti: String,
    @SerialName("lokasi") val lokasi: String,
    @SerialName("harga") val harga: String,
    @SerialName("status_properti") val statusProperti: String,

    @SerialName("id_jenis") val idJenis: String,
    @SerialName("id_pemilik") val idPemilik: String,
    @SerialName("id_manajer") val idManajer: String,
)
