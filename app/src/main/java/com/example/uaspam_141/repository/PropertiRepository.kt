package com.example.uaspam_141.repository

import com.example.uaspam_141.model.Properti
import com.example.uaspam_141.service.PropertiService
import java.io.IOException

interface PropertiRepository {
    suspend fun getProperti(): List<Properti>
    suspend fun insertProperti(properti: Properti)
    suspend fun updateProperti(id_properti: String, properti: Properti)
    suspend fun deleteProperti(id_properti: String)
    suspend fun getPropertiById(id_properti: String): Properti
}

class NetworkPropertiRepository(
    private val propertiAPIService: PropertiService
) : PropertiRepository {

    override suspend fun getProperti(): List<Properti> {
        try {
            return propertiAPIService.getProperti()
        } catch (e: IOException) {
            throw IOException("Failed to fetch properti list. Network error occurred.", e)
        }
    }

    override suspend fun getPropertiById(id_properti: String): Properti {
        try {
            return propertiAPIService.getPropertiById(id_properti)
        } catch (e: IOException) {
            throw IOException("Failed to fetch properti with id_properti: $id_properti. Network error occurred.", e)
        }
    }

    override suspend fun insertProperti(properti: Properti) {
        try {
            val response = propertiAPIService.insertProperti(properti)
            if (!response.isSuccessful) {
                throw IOException("Failed to insert properti. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to insert properti. Network error occurred.", e)
        }
    }

    override suspend fun updateProperti(id_properti: String, properti: Properti) {
        try {
            val response = propertiAPIService.updateProperti(id_properti, properti)
            if (!response.isSuccessful) {
                throw IOException("Failed to update properti with id_properti: $id_properti. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to update properti. Network error occurred.", e)
        }
    }

    override suspend fun deleteProperti(id_properti: String) {
        try {
            val response = propertiAPIService.deleteProperti(id_properti)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete properti with id_properti: $id_properti. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to delete properti. Network error occurred.", e)
        }
    }
}