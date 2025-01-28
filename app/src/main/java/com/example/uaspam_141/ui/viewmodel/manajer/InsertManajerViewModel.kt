package com.example.uaspam_141.ui.viewmodel.manajer


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.repository.ManajerRepository
import kotlinx.coroutines.launch

class InsertManajerViewModel(private val manajerRepository: ManajerRepository): ViewModel() {
    var uiState by mutableStateOf(InsertManajerUiState())
        private set

    fun updateInsertManajerState(insertUiEvent: InsertManajerUiEvent) {
        uiState = InsertManajerUiState(insertUiEvent = insertUiEvent)
    }

    fun isFormValid(): Boolean {
        return uiState.insertUiEvent.run {
            id_manajer.isNotBlank() &&
                    nama_manajer.isNotBlank() &&
                    kontak_manajer.isNotBlank()
        }
    }

    fun insertManajer() {
        viewModelScope.launch {
            try {
                manajerRepository.insertManajer(uiState.insertUiEvent.toManajer())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertManajerUiState(
    val insertUiEvent: InsertManajerUiEvent = InsertManajerUiEvent()
)

data class InsertManajerUiEvent(
    val id_manajer: String = "",
    val nama_manajer: String = "",
    val kontak_manajer: String = ""
)

fun InsertManajerUiEvent.toManajer(): Manajer = Manajer(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer
)

fun Manajer.toUiStateManajer(): InsertManajerUiState = InsertManajerUiState(
    insertUiEvent = toInsertManajerUiEvent()
)

fun Manajer.toInsertManajerUiEvent(): InsertManajerUiEvent = InsertManajerUiEvent(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer
)