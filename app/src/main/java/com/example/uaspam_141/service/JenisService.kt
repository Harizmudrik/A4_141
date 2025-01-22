package com.example.uaspam_141.service

import com.example.uaspam_141.model.Jenis
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface JenisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("baca1jenis.php")
    suspend fun getJenis(): List<Jenis>

    @GET("bacajenis.php/{id_jenis}")
    suspend fun getJenisById(@Query("id_jenis") idJenis: String): Jenis

    @POST("insertjenis.php")
    suspend fun insertJenis(@Body jenis: Jenis): Response<Void>

    @PUT("editjenis.php/{id_jenis}")
    suspend fun updateJenis(
        @Query("id_jenis") idJenis: String,
        @Body jenis: Jenis
    ): Response<Void>

    @DELETE("deletejenis.php/{id_jenis}")
    suspend fun deleteJenis(@Query("id_jenis") idJenis: String): Response<Void>
}