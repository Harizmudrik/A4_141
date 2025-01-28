package com.example.uaspam_141.ui.viewmodel.properti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Properti
import com.example.uaspam_141.repository.PropertiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPropertiUiState {
    object Loading : DetailPropertiUiState()
    data class Success(val properti: Properti) : DetailPropertiUiState()
    object Error : DetailPropertiUiState()
}

class DetailPropertiViewModel(private val repository: PropertiRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailPropertiUiState>(DetailPropertiUiState.Loading)
    val uiState: StateFlow<DetailPropertiUiState> = _uiState

    fun getPropertiById(id_properti: String) {
        viewModelScope.launch {
            _uiState.value = DetailPropertiUiState.Loading
            try {
                val properti = repository.getPropertiById(id_properti)
                _uiState.value = DetailPropertiUiState.Success(properti)
            } catch (e: IOException) {
                e.printStackTrace()
                _uiState.value = DetailPropertiUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _uiState.value = DetailPropertiUiState.Error
            }
        }
    }
}
