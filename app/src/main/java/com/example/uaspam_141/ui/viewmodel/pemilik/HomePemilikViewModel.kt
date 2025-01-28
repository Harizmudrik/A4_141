package com.example.uaspam_141.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Pemilik
import com.example.uaspam_141.repository.PemilikRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePemilikUiState{
    data class Success(val pemilik: List<Pemilik>): HomePemilikUiState()
    object Error: HomePemilikUiState()
    object Loading: HomePemilikUiState()
}

class HomePemilikViewModel (private val pemilikRepository: PemilikRepository): ViewModel(){
    var pemilikUiState: HomePemilikUiState by mutableStateOf(HomePemilikUiState.Loading)
        private set

    init {
        getPemilik()
    }

    fun getPemilik(){
        viewModelScope.launch {
            pemilikUiState = HomePemilikUiState.Loading
            pemilikUiState = try {
                HomePemilikUiState.Success(pemilikRepository.getPemilik())
                HomePemilikUiState.Success(pemilikRepository.getPemilik())
            }catch (e: IOException){
                HomePemilikUiState.Error
            }catch (e: HttpException){
                HomePemilikUiState.Error
            }
        }
    }

    fun deletePemilik(id_pemilik: String){
        viewModelScope.launch {
            try {
                pemilikRepository.deletePemilik(id_pemilik)
            }catch (e: IOException){
                HomePemilikUiState.Error
            }catch (e: HttpException){
                HomePemilikUiState.Error
            }
        }
    }
}