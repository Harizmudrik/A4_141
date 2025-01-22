package com.example.uaspam_141.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Jenis (
    @SerialName("id_jenis") val idJenis: String,
    @SerialName("nama_jenis") val namaJenis: String,
    @SerialName("deskripsi_jenis") val deskripsi: String
)