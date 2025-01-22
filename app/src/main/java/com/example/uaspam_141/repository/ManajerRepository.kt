package com.example.uaspam_141.repository

import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.service.ManajerService
import java.io.IOException

interface ManajerRepository {
    suspend fun getManajer(): List<Manajer>
    suspend fun insertManajer(manajer: Manajer)
    suspend fun updateManajer(id_manajer: String, manajer: Manajer)
    suspend fun deleteManajer(id_manajer: String)
    suspend fun getManajerById(id_manajer: String): Manajer
}

class NetworkManajerRepository(
    private val manajerAPIService: ManajerService
) : ManajerRepository {

    override suspend fun getManajer(): List<Manajer> {
        try {
            return manajerAPIService.getManajer()
        } catch (e: IOException) {
            throw IOException("Failed to fetch manajer list. Network error occurred.", e)
        }
    }

    override suspend fun getManajerById(id_manajer: String): Manajer {
        try {
            return manajerAPIService.getManajerById(id_manajer)
        } catch (e: IOException) {
            throw IOException("Failed to fetch manajer with id_manajer: $id_manajer. Network error occurred.", e)
        }
    }

    override suspend fun insertManajer(manajer: Manajer) {
        try {
            val response = manajerAPIService.insertManajer(manajer)
            if (!response.isSuccessful) {
                throw IOException("Failed to insert manajer. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to insert manajer. Network error occurred.", e)
        }
    }

    override suspend fun updateManajer(id_manajer: String, manajer: Manajer) {
        try {
            val response = manajerAPIService.updateManajer(id_manajer, manajer)
            if (!response.isSuccessful) {
                throw IOException("Failed to update manajer with id_manajer: $id_manajer. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to update manajer. Network error occurred.", e)
        }
    }

    override suspend fun deleteManajer(id_manajer: String) {
        try {
            val response = manajerAPIService.deleteManajer(id_manajer)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete manajer with id_manajer: $id_manajer. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to delete manajer. Network error occurred.", e)
        }
    }
}