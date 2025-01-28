package com.example.uaspam_141.ui.viewmodel.jenis

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Jenis
import com.example.uaspam_141.repository.JenisRepository
import kotlinx.coroutines.launch

class UpdateJenisViewModel(private val jenisRepository: JenisRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateJenisUiState())
        private set

    fun loadJenis(id_jenis: String) {
        viewModelScope.launch {
            try {
                val jenis = jenisRepository.getJenisById(id_jenis)
                uiState = UpdateJenisUiState(updateJenisEvent = jenis.toUpdateJenisEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateJenisState(updateJenisEvent: UpdateJenisEvent) {
        uiState = UpdateJenisUiState(updateJenisEvent = updateJenisEvent)
    }

    fun updateJenis(id_jenis: String) {
        viewModelScope.launch {
            try {
                jenisRepository.updateJenis(id_jenis, uiState.updateJenisEvent.toJenis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateJenisUiState(
    val updateJenisEvent: UpdateJenisEvent = UpdateJenisEvent()
)

data class UpdateJenisEvent(
    val id_jenis: String = "",
    val nama_jenis: String = "",
    val deskripsi_jenis: String = ""
)

fun UpdateJenisEvent.toJenis(): Jenis = Jenis(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis
)

fun Jenis.toUpdateJenisEvent(): UpdateJenisEvent = UpdateJenisEvent(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis
)
