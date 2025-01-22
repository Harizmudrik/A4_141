package com.example.uaspam_141.repository

import com.example.uaspam_141.model.Pemilik
import java.io.IOException

interface PemilikRepository {
    suspend fun getPemilik(): List<Pemilik>
    suspend fun insertPemilik(pemilik: Pemilik)
    suspend fun updatePemilik(id_pemilik: String, pemilik: Pemilik)
    suspend fun deletePemilik(id_pemilik: String)
    suspend fun getPemilikById(id_pemilik: String): Pemilik
}

class NetworkPemilikRepository(
    private val pemilikAPIService: PemilikService
) : PemilikRepository {

    override suspend fun getPemilik(): List<Pemilik> {
        try {
            return pemilikAPIService.getPemilik()
        } catch (e: IOException) {
            throw IOException("Failed to fetch pemilik list. Network error occurred.", e)
        }
    }

    override suspend fun getPemilikById(id_pemilik: String): Pemilik {
        try {
            return pemilikAPIService.getPemilikById(id_pemilik)
        } catch (e: IOException) {
            throw IOException("Failed to fetch pemilik with id_pemilik: $id_pemilik. Network error occurred.", e)
        }
    }

    override suspend fun insertPemilik(pemilik: Pemilik) {
        try {
            val response = pemilikAPIService.insertPemilik(pemilik)
            if (!response.isSuccessful) {
                throw IOException("Failed to insert pemilik. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to insert pemilik. Network error occurred.", e)
        }
    }

    override suspend fun updatePemilik(id_pemilik: String, pemilik: Pemilik) {
        try {
            val response = pemilikAPIService.updatePemilik(id_pemilik, pemilik)
            if (!response.isSuccessful) {
                throw IOException("Failed to update pemilik with id_pemilik: $id_pemilik. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to update pemilik. Network error occurred.", e)
        }
    }

    override suspend fun deletePemilik(id_pemilik: String) {
        try {
            val response = pemilikAPIService.deletePemilik(id_pemilik)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pemilik with id_pemilik: $id_pemilik. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to delete pemilik. Network error occurred.", e)
        }
    }
}