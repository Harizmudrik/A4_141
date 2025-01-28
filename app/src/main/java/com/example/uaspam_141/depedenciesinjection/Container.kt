package com.example.uaspam_141.depedenciesinjection

import com.example.uaspam_141.repository.JenisRepository
import com.example.uaspam_141.repository.ManajerRepository
import com.example.uaspam_141.repository.NetworkJenisRepository
import com.example.uaspam_141.repository.NetworkManajerRepository
import com.example.uaspam_141.repository.NetworkPemilikRepository
import com.example.uaspam_141.repository.NetworkPropertiRepository
import com.example.uaspam_141.repository.PemilikRepository
import com.example.uaspam_141.repository.PropertiRepository
import com.example.uaspam_141.service.JenisService
import com.example.uaspam_141.service.ManajerService
import com.example.uaspam_141.service.PemilikService
import com.example.uaspam_141.service.PropertiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val propertiRepository: PropertiRepository
    val jenisRepository: JenisRepository
    val manajerRepository: ManajerRepository
    val pemilikRepository: PemilikRepository
}

class Container: AppContainer{
    private val baseUrl = "http://10.0.2.2/properti/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val propertiService: PropertiService by lazy { retrofit.create(PropertiService::class.java) }
    override val propertiRepository: PropertiRepository by lazy { NetworkPropertiRepository(propertiService) }

    private val jenisService: JenisService by lazy { retrofit.create(JenisService::class.java) }
    override val jenisRepository: JenisRepository by lazy { NetworkJenisRepository(jenisService) }

    private val manajerService: ManajerService by lazy { retrofit.create(ManajerService::class.java) }
    override val manajerRepository: ManajerRepository by lazy { NetworkManajerRepository(manajerService) }

    private val pemilikService: PemilikService by lazy { retrofit.create(PemilikService::class.java) }
    override val pemilikRepository: PemilikRepository by lazy { NetworkPemilikRepository(pemilikService) }
}