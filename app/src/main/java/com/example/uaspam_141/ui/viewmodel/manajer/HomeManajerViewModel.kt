package com.example.uaspam_141.ui.viewmodel.manajer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam_141.model.Manajer
import com.example.uaspam_141.repository.ManajerRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeManajerUiState{
    data class Success(val manajer: List<Manajer>): HomeManajerUiState()
    object Error: HomeManajerUiState()
    object Loading: HomeManajerUiState()
}

class HomeManajerViewModel (private val manajerRepository: ManajerRepository): ViewModel(){
    var manajerUiState: HomeManajerUiState by mutableStateOf(HomeManajerUiState.Loading)
        private set

    init {
        getManajer()
    }

    fun getManajer(){
        viewModelScope.launch {
            manajerUiState = HomeManajerUiState.Loading
            manajerUiState = try {
                HomeManajerUiState.Success(manajerRepository.getManajer())
            }catch (e: IOException){
                HomeManajerUiState.Error
            }catch (e: HttpException){
                HomeManajerUiState.Error
            }
        }
    }

    fun deleteManajer(id_manajer: String){
        viewModelScope.launch {
            try {
                manajerRepository.deleteManajer(id_manajer)
            }catch (e: IOException){
                HomeManajerUiState.Error
            }catch (e: HttpException){
                HomeManajerUiState.Error
            }
        }
    }
}