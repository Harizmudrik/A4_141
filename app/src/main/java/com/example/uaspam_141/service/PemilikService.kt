package com.example.uaspam_141.service

import com.example.uaspam_141.model.Pemilik
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PemilikService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacapemilik.php")
    suspend fun getPemilik(): List<Pemilik>

    @GET("baca1pemilik.php/{id_pemilik}")
    suspend fun getPemilikById(@Query("id_pemilik") idPemilik: String): Pemilik

    @POST("insertpemilik.php")
    suspend fun insertPemilik(@Body pemilik: Pemilik): Response<Void>

    @PUT("editpemilik.php/{id_pemilik}")
    suspend fun updatePemilik(
        @Query("id_pemilik") idPemilik: String,
        @Body pemilik: Pemilik
    ): Response<Void>

    @DELETE("deletepemilik.php/{id_pemilik}")
    suspend fun deletePemilik(@Query("id_pemilik") idPemilik: String): Response<Void>
}