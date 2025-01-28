package com.example.uaspam_141.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Properti
import com.example.uaspam_141.repository.PropertiRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePropertiUiState {
    data class Success(val properti: List<Properti>) : HomePropertiUiState()
    object Error : HomePropertiUiState()
    object Loading : HomePropertiUiState()
}

class HomePropertiViewModel(private val propertiRepository: PropertiRepository) : ViewModel() {
    var propertiUiState: HomePropertiUiState by mutableStateOf(HomePropertiUiState.Loading)
        private set

    init {
        getProperti()
    }

    // Mendapatkan daftar properti
    fun getProperti() {
        viewModelScope.launch {
            propertiUiState = HomePropertiUiState.Loading
            propertiUiState = try {
                HomePropertiUiState.Success(propertiRepository.getProperti())
            } catch (e: IOException) {
                HomePropertiUiState.Error
            } catch (e: HttpException) {
                HomePropertiUiState.Error
            }
        }
    }

    // Menambahkan properti baru
    fun addProperti(properti: Properti) {
        viewModelScope.launch {
            try {
                propertiRepository.insertProperti(properti)
                getProperti() // Refresh data setelah penambahan
            } catch (e: IOException) {
                propertiUiState = HomePropertiUiState.Error
            } catch (e: HttpException) {
                propertiUiState = HomePropertiUiState.Error
            }
        }
    }

    // Menghapus properti berdasarkan ID
    fun deleteProperti(id_properti: String) {
        viewModelScope.launch {
            try {
                propertiRepository.deleteProperti(id_properti)
                getProperti() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                propertiUiState = HomePropertiUiState.Error
            } catch (e: HttpException) {
                propertiUiState = HomePropertiUiState.Error
            }
        }
    }
}
