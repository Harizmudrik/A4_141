package com.example.uaspam_141.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Jenis
import com.example.uaspam_141.repository.JenisRepository
import kotlinx.coroutines.launch

class InsertJenisViewModel(private val jenisRepository: JenisRepository): ViewModel() {
    var uiState by mutableStateOf(InsertJenisUiState())
        private set

    fun updateInsertJenisState(insertUiEvent: InsertJenisUiEvent) {
        uiState = InsertJenisUiState(insertUiEvent = insertUiEvent)
    }

    fun isFormValid(): Boolean {
        return uiState.insertUiEvent.run {
            id_jenis.isNotBlank() &&
                    nama_jenis.isNotBlank() &&
                    deskripsi_jenis.isNotBlank()
        }
    }

    fun insertJenis() {
        viewModelScope.launch {
            try {
                jenisRepository.insertJenis(uiState.insertUiEvent.toJenis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertJenisUiState(
    val insertUiEvent: InsertJenisUiEvent = InsertJenisUiEvent()
)

data class InsertJenisUiEvent(
    val id_jenis: String = "",
    val nama_jenis: String = "",
    val deskripsi_jenis: String = ""
)

fun InsertJenisUiEvent.toJenis(): Jenis = Jenis(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis
)

fun Jenis.toUiStateJenis(): InsertJenisUiState = InsertJenisUiState(
    insertUiEvent = toInsertJenisUiEvent()
)

fun Jenis.toInsertJenisUiEvent(): InsertJenisUiEvent = InsertJenisUiEvent(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis
)