package com.example.uaspam_141.model

import kotlinx.serialization.Serializable

@Serializable
data class Pemilik(
    val id_pemilik: String,
    val nama_pemilik: String,
    val kontak_pemilik: String
)
