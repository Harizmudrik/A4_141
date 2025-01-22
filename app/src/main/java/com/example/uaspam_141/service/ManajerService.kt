package com.example.uaspam_141.service

import com.example.uaspam_141.model.Manajer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ManajerService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacamanajer.php")
    suspend fun getManajer(): List<Manajer>

    @GET("baca1manajer.php/{id_manajer}")
    suspend fun getManajerById(@Query("id_manajer") idManajer: String): Manajer

    @POST("insertmanajer.php")
    suspend fun insertManajer(@Body manajer: Manajer): Response<Void>

    @PUT("editmanajer.php/{id_manajer}")
    suspend fun updateManajer(
        @Query("id_manajer") idManajer: String,
        @Body manajer: Manajer
    ): Response<Void>

    @DELETE("deletemanajer.php/{id_manajer}")
    suspend fun deleteManajer(@Query("id_manajer") idManajer: String): Response<Void>
}