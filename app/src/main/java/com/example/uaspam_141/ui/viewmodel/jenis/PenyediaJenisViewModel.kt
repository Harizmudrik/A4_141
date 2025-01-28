package com.example.uaspam_141.ui.viewmodel.jenis

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam_141.PropertiApplication


object PenyediaJenisViewModel{
    val Factory = viewModelFactory {
        initializer { HomeJenisViewModel(aplikasiJenis().container.jenisRepository) }
        initializer { InsertJenisViewModel(aplikasiJenis().container.jenisRepository) }
        initializer { UpdateJenisViewModel(aplikasiJenis().container.jenisRepository) }
        initializer { DetailJenisViewModel(aplikasiJenis().container.jenisRepository) }
    }
}

fun CreationExtras.aplikasiJenis(): PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropertiApplication)