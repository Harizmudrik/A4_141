package com.example.uaspam_141.repository

import com.example.uaspam_141.model.Jenis
import java.io.IOException

interface JenisRepository {
    suspend fun getJenis(): List<Jenis>
    suspend fun insertJenis(jenis: Jenis)
    suspend fun updateJenis(id_jenis: String, jenis: Jenis)
    suspend fun deleteJenis(id_jenis: String)
    suspend fun getJenisById(id_jenis: String): Jenis
}

class NetworkJenisRepository(
    private val jenisAPIService: JenisService
) : JenisRepository {

    override suspend fun getJenis(): List<Jenis> {
        try {
            return jenisAPIService.getJenis()
        } catch (e: IOException) {
            throw IOException("Failed to fetch jenis list. Network error occurred.", e)
        }
    }

    override suspend fun getJenisById(id_jenis: String): Jenis {
        try {
            return jenisAPIService.getJenisById(id_jenis)
        } catch (e: IOException) {
            throw IOException("Failed to fetch jenis with id_jenis: $id_jenis. Network error occurred.", e)
        }
    }

    override suspend fun insertJenis(jenis: Jenis) {
        try {
            val response = jenisAPIService.insertJenis(jenis)
            if (!response.isSuccessful) {
                throw IOException("Failed to insert jenis. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to insert jenis. Network error occurred.", e)
        }
    }

    override suspend fun updateJenis(id_jenis: String, jenis: Jenis) {
        try {
            val response = jenisAPIService.updateJenis(id_jenis, jenis)
            if (!response.isSuccessful) {
                throw IOException("Failed to update jenis with id_jenis: $id_jenis. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to update jenis. Network error occurred.", e)
        }
    }

    override suspend fun deleteJenis(id_jenis: String) {
        try {
            val response = jenisAPIService.deleteJenis(id_jenis)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete jenis with id_jenis: $id_jenis. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to delete jenis. Network error occurred.", e)
        }
    }
}