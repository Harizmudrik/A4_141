package com.example.uaspam_141.ui.viewmodel.pemilik

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Pemilik
import com.example.uaspam_141.repository.PemilikRepository
import kotlinx.coroutines.launch

class UpdatePemilikViewModel(private val pemilikRepository: PemilikRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdatePemilikUiState())
        private set

    fun loadPemilik(id_pemilik: String) {
        viewModelScope.launch {
            try {
                val pemilik = pemilikRepository.getPemilikById(id_pemilik)
                uiState = UpdatePemilikUiState(updatePemilikEvent = pemilik.toUpdatePemilikEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePemilikState(updatePemilikEvent: UpdatePemilikEvent) {
        uiState = UpdatePemilikUiState(updatePemilikEvent = updatePemilikEvent)
    }

    fun updatePemilik(id_pemilik: String) {
        viewModelScope.launch {
            try {
                pemilikRepository.updatePemilik(id_pemilik, uiState.updatePemilikEvent.toPemilik())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatePemilikUiState(
    val updatePemilikEvent: UpdatePemilikEvent = UpdatePemilikEvent()
)

data class UpdatePemilikEvent(
    val id_pemilik: String = "",
    val nama_pemilik: String = "",
    val kontak_pemilik: String = ""
)

fun UpdatePemilikEvent.toPemilik(): Pemilik = Pemilik(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik
)

fun Pemilik.toUpdatePemilikEvent(): UpdatePemilikEvent = UpdatePemilikEvent(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik
)
