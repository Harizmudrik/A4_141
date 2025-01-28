package com.example.uaspam_141.service

import com.example.uaspam_141.model.Jenis
import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.model.Pemilik
import com.example.uaspam_141.model.Properti
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PropertiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("bacajenis")
    suspend fun getJenis(): List<Jenis>

    @GET("bacapemilik")
    suspend fun getPemilik(): List<Pemilik>

    @GET("bacamanajer")
    suspend fun getManajer(): List<Manajer>

    @GET("bacaproperti.php")
    suspend fun getProperti(): List<Properti>

    @GET("baca1properti.php/{id_properti}")
    suspend fun getPropertiById(@Query("id_properti") idProperti: String): Properti

    @POST("insertproperti.php")
    suspend fun insertProperti(@Body properti: Properti): Response<Void>

    @PUT("editproperti.php/{id_properti}")
    suspend fun updateProperti(
        @Query("id_properti") idProperti: String,
        @Body properti: Properti
    ): Response<Void>

    @DELETE("deleteproperti.php/{id_properti}")
    suspend fun deleteProperti(@Query("id_properti") idProperti: String): Response<Void>
}