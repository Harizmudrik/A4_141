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

    fun loadPemilik(idPemilik: String) {
        viewModelScope.launch {
            try {
                val pemilik = pemilikRepository.getPemilikById(idPemilik)
                uiState = UpdatePemilikUiState(updatePemilikEvent = pemilik.toUpdatePemilikEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePemilikState(updatePemilikEvent: UpdatePemilikEvent) {
        uiState = UpdatePemilikUiState(updatePemilikEvent = updatePemilikEvent)
    }

    suspend fun updatePemilik(idPemilik: String) {
        viewModelScope.launch {
            try {
                pemilikRepository.updatePemilik(idPemilik, uiState.updatePemilikEvent.toPemilik())
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
    val idPemilik: String = "",
    val namaPemilik: String = "",
    val kontakPemilik: String = ""
)

fun UpdatePemilikEvent.toPemilik(): Pemilik = Pemilik(
    idPemilik = idPemilik,
    namaPemilik = namaPemilik,
    kontakPemilik = kontakPemilik
)

fun Pemilik.toUpdatePemilikEvent(): UpdatePemilikEvent = UpdatePemilikEvent(
    idPemilik = idPemilik,
    namaPemilik = namaPemilik,
    kontakPemilik = kontakPemilik
)
