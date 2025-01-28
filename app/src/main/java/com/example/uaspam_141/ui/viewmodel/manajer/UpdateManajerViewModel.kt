package com.example.uaspam_141.ui.viewmodel.manajer

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.repository.ManajerRepository
import kotlinx.coroutines.launch

class UpdateManajerViewModel(private val manajerRepository: ManajerRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateManajerUiState())
        private set

    fun loadManajer(id_manajer: String) {
        viewModelScope.launch {
            try {
                val manajer = manajerRepository.getManajerById(id_manajer)
                uiState = UpdateManajerUiState(updateManajerEvent = manajer.toUpdateManajerEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateManajerState(updateManajerEvent: UpdateManajerEvent) {
        uiState = UpdateManajerUiState(updateManajerEvent = updateManajerEvent)
    }

    fun updateManajer(id_manajer: String) {
        viewModelScope.launch {
            try {
                manajerRepository.updateManajer(id_manajer, uiState.updateManajerEvent.toManajer())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateManajerUiState(
    val updateManajerEvent: UpdateManajerEvent = UpdateManajerEvent()
)

data class UpdateManajerEvent(
    val id_manajer: String = "",
    val nama_manajer: String = "",
    val kontak_manajer: String = ""
)

fun UpdateManajerEvent.toManajer(): Manajer = Manajer(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer
)

fun Manajer.toUpdateManajerEvent(): UpdateManajerEvent = UpdateManajerEvent(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer
)
