package com.example.uaspam_141.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Jenis
import com.example.uaspam_141.repository.JenisRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeJenisUiState{
    data class Success(val jenis: List<Jenis>): HomeJenisUiState()
    object Error: HomeJenisUiState()
    object Loading: HomeJenisUiState()
}

class HomeJenisViewModel (private val jenisRepository: JenisRepository): ViewModel(){
    var jenisUiState: HomeJenisUiState by mutableStateOf(HomeJenisUiState.Loading)
        private set

    init {
        getJenis()
    }

    fun getJenis(){
        viewModelScope.launch {
            jenisUiState = HomeJenisUiState.Loading
            jenisUiState = try {
                HomeJenisUiState.Success(jenisRepository.getJenis())
            }catch (e: IOException){
                HomeJenisUiState.Error
            }catch (e: HttpException){
                HomeJenisUiState.Error
            }
        }
    }

    fun deleteJenis(id_jenis: String){
        viewModelScope.launch {
            try {
                jenisRepository.deleteJenis(id_jenis)
            }catch (e: IOException){
                HomeJenisUiState.Error
            }catch (e: HttpException){
                HomeJenisUiState.Error
            }
        }
    }
}