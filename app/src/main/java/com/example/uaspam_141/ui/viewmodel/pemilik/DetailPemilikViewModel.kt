package com.example.uaspam_141.ui.viewmodel.pemilik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Pemilik
import com.example.uaspam_141.repository.PemilikRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPemilikUiState {
    object Loading : DetailPemilikUiState()
    data class Success(val pemilik: Pemilik) : DetailPemilikUiState()
    object Error : DetailPemilikUiState()
}

class DetailPemilikViewModel(private val repository: PemilikRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailPemilikUiState>(DetailPemilikUiState.Loading)
    val uiState: StateFlow<DetailPemilikUiState> = _uiState

    fun getPemilikById(id_pemilik: String) {
        viewModelScope.launch {
            _uiState.value = DetailPemilikUiState.Loading
            try {
                val pemilik = repository.getPemilikById(id_pemilik)
                _uiState.value = DetailPemilikUiState.Success(pemilik)
            } catch (e: IOException) {
                e.printStackTrace()
                _uiState.value = DetailPemilikUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _uiState.value = DetailPemilikUiState.Error
            }
        }
    }
}
