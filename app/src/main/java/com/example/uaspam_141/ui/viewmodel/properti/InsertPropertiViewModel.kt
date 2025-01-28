package com.example.uaspam_141.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam_141.model.Properti
import com.example.uaspam_141.repository.PropertiRepository
import kotlinx.coroutines.launch

class InsertPropertiViewModel(
    private val propertiRepository: PropertiRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPropertiUiState())
        private set

    // Memperbarui UI state berdasarkan event input pengguna
    fun updateInsertPropertiState(insertUiEvent: InsertPropertiUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    // Validasi form input sebelum pengiriman data
    fun isFormValid(): Boolean {
        return uiState.insertUiEvent.run {
            id_properti.isNotBlank() &&
                    nama_properti.isNotBlank() &&
                    deskripsi_properti.isNotBlank() &&
                    lokasi.isNotBlank() &&
                    harga.isNotBlank() &&
                    status_properti.isNotBlank() &&
                    id_jenis.isNotBlank() &&
                    id_pemilik.isNotBlank() &&
                    id_manajer.isNotBlank()
        }
    }

    // Fungsi untuk menyimpan properti baru ke repository
    fun insertProperti() {
        viewModelScope.launch {
            try {
                // Validasi form sebelum menyimpan
                if (!isFormValid()) {
                    throw Exception("Form tidak valid. Pastikan semua field telah diisi dengan benar.")
                }

                // Konversi event UI menjadi model Properti
                val properti = uiState.insertUiEvent.toProperti()

                // Simpan properti ke repository
                propertiRepository.insertProperti(properti)

                // Reset UI state setelah operasi berhasil
                uiState = InsertPropertiUiState()

            } catch (e: Exception) {
                // Tangani kesalahan (misalnya, tampilkan pesan error ke UI)
                e.printStackTrace()
            }
        }
    }

}

data class InsertPropertiUiState(
    val insertUiEvent: InsertPropertiUiEvent = InsertPropertiUiEvent()
)

data class InsertPropertiUiEvent(
    val id_properti: String = "",
    val nama_properti: String = "",
    val deskripsi_properti: String = "",
    val lokasi: String = "",
    val harga: String = "", // Masih dalam bentuk String untuk validasi input
    val status_properti: String = "",
    val id_jenis: String = "",
    val id_pemilik: String = "",
    val id_manajer: String = ""
)

fun InsertPropertiUiEvent.toProperti(): Properti = Properti(
    id_properti = id_properti,
    nama_properti = nama_properti,
    deskripsi_properti = deskripsi_properti,
    lokasi = lokasi,
    harga = harga.toDoubleOrNull() ?: 0.0, // Konversi String ke Double
    status_properti = status_properti,
    id_jenis = id_jenis,
    id_pemilik = id_pemilik,
    id_manajer = id_manajer
)

fun Properti.toUiStateProperti(): InsertPropertiUiState = InsertPropertiUiState(
    insertUiEvent = toInsertPropertiUiEvent()
)

fun Properti.toInsertPropertiUiEvent(): InsertPropertiUiEvent = InsertPropertiUiEvent(
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
