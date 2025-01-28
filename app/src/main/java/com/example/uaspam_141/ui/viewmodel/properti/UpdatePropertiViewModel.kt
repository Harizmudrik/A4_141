package com.example.uaspam_141.ui.viewmodel.properti

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Properti
import com.example.uaspam_141.repository.PropertiRepository
import kotlinx.coroutines.launch

class UpdatePropertiViewModel(private val propertiRepository: PropertiRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdatePropertiUiState())
        private set

    // Memuat data properti berdasarkan ID
    fun loadProperti(id_properti: String) {
        viewModelScope.launch {
            try {
                val properti = propertiRepository.getPropertiById(id_properti)
                uiState = UpdatePropertiUiState(updatePropertiEvent = properti.toUpdatePropertiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui data di UiState
    fun updatePropertiState(updatePropertiEvent: UpdatePropertiEvent) {
        uiState = UpdatePropertiUiState(updatePropertiEvent = updatePropertiEvent)
    }

    // Menyimpan pembaruan data properti
    fun updateProperti(id_properti: String) {
        viewModelScope.launch {
            try {
                propertiRepository.updateProperti(id_properti, uiState.updatePropertiEvent.toProperti())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// Data class untuk menyimpan state UI
data class UpdatePropertiUiState(
    val updatePropertiEvent: UpdatePropertiEvent = UpdatePropertiEvent()
)

// Event data untuk properti
data class UpdatePropertiEvent(
    val id_properti: String = "",
    val nama_properti: String = "",
    val deskripsi_properti: String = "",
    val lokasi: String = "",
    val harga: String = "", // Tetap string untuk validasi input
    val status_properti: String = "",
    val id_jenis: String = "",
    val id_pemilik: String = "",
    val id_manajer: String = ""
)

// Konversi dari UpdatePropertiEvent ke Properti
fun UpdatePropertiEvent.toProperti(): Properti = Properti(
    id_properti = id_properti,
    nama_properti = nama_properti,
    deskripsi_properti = deskripsi_properti,
    lokasi = lokasi,
    harga = harga.toDoubleOrNull() ?: 0.0, // Konversi string ke double
    status_properti = status_properti,
    id_jenis = id_jenis,
    id_pemilik = id_pemilik,
    id_manajer = id_manajer
)

// Konversi dari Properti ke UpdatePropertiEvent
fun Properti.toUpdatePropertiEvent(): UpdatePropertiEvent = UpdatePropertiEvent(
    id_properti = id_properti,
    nama_properti = nama_properti,
    deskripsi_properti = deskripsi_properti,
    lokasi = lokasi,
    harga = harga.toString(),
    status_properti = status_properti,
    id_jenis = id_jenis,
    id_pemilik = id_pemilik,
    id_manajer = id_manajer
)
