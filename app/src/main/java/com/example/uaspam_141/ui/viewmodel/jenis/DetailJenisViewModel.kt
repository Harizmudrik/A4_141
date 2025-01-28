package com.example.uaspam_141.ui.viewmodel.jenis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Jenis
import com.example.uaspam_141.repository.JenisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailJenisUiState {
    object Loading : DetailJenisUiState()
    data class Success(val jenis: Jenis) : DetailJenisUiState()
    object Error : DetailJenisUiState()
}

class DetailJenisViewModel(private val repository: JenisRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailJenisUiState>(DetailJenisUiState.Loading)
    val uiState: StateFlow<DetailJenisUiState> = _uiState

    fun getJenisById(id_jenis: String) {
        viewModelScope.launch {
            _uiState.value = DetailJenisUiState.Loading
            try {
                val jenis = repository.getJenisById(id_jenis)
                _uiState.value = DetailJenisUiState.Success(jenis)
            } catch (e: IOException) {
                e.printStackTrace()
                _uiState.value = DetailJenisUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _uiState.value = DetailJenisUiState.Error
            }
        }
    }
}
