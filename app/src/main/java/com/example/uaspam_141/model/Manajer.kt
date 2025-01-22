package com.example.uaspam_141.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Manajer(
    @SerialName("id_manajer") val idmanajer: String,
    @SerialName("nama_manajer") val namaManajer: String,
    @SerialName("kontak_manajer") val kontakManajer: String
)
