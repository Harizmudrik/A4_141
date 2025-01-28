package com.example.uaspam_141.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Pemilik
import com.example.uaspam_141.repository.PemilikRepository
import kotlinx.coroutines.launch

class InsertPemilikViewModel(private val pemilikRepository: PemilikRepository): ViewModel() {
    var uiState by mutableStateOf(InsertPemilikUiState())
        private set

    fun updateInsertPemilikState(insertUiEvent: InsertPemilikUiEvent) {
        uiState = InsertPemilikUiState(insertUiEvent = insertUiEvent)
    }

    fun isFormValid(): Boolean {
        return uiState.insertUiEvent.run {
            id_pemilik.isNotBlank() &&
                    nama_pemilik.isNotBlank() &&
                    kontak_pemilik.isNotBlank()
        }
    }

     fun insertPemilik() {
        viewModelScope.launch {
            try {
                pemilikRepository.insertPemilik(uiState.insertUiEvent.toPemilik())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPemilikUiState(
    val insertUiEvent: InsertPemilikUiEvent = InsertPemilikUiEvent()
)

data class InsertPemilikUiEvent(
    val id_pemilik: String = "",
    val nama_pemilik: String = "",
    val kontak_pemilik: String = ""
)

fun InsertPemilikUiEvent.toPemilik(): Pemilik = Pemilik(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik
)

fun Pemilik.toUiStatePemilik(): InsertPemilikUiState = InsertPemilikUiState(
    insertUiEvent = toInsertPemilikUiEvent()
)

fun Pemilik.toInsertPemilikUiEvent(): InsertPemilikUiEvent = InsertPemilikUiEvent(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik
)