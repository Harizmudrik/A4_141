package com.example.uaspam_141.ui.viewmodel.manajer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.repository.ManajerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailManajerUiState {
    object Loading : DetailManajerUiState()
    data class Success(val manajer: Manajer) : DetailManajerUiState()
    object Error : DetailManajerUiState()
}

class DetailManajerViewModel(private val repository: ManajerRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailManajerUiState>(DetailManajerUiState.Loading)
    val uiState: StateFlow<DetailManajerUiState> = _uiState

    fun getManajerById(id_manajer: String) {
        viewModelScope.launch {
            _uiState.value = DetailManajerUiState.Loading
            try {
                val manajer = repository.getManajerById(id_manajer)
                _uiState.value = DetailManajerUiState.Success(manajer)
            } catch (e: IOException) {
                e.printStackTrace()
                _uiState.value = DetailManajerUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _uiState.value = DetailManajerUiState.Error
            }
        }
    }
}
