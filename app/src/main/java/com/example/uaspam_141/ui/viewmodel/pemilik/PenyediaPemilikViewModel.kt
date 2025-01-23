package com.example.uaspam_141.ui.viewmodel.pemilik

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam_141.PropertiApplication

object PenyediaPemilikViewModel{
    val Factory = viewModelFactory {
        initializer { HomePemilikViewModel(aplikasiPemilik().container.pemilikRepository) }
        initializer { InsertPemilikViewModel(aplikasiPemilik().container.pemilikRepository) }
        initializer { UpdatePemilikViewModel(aplikasiPemilik().container.pemilikRepository) }
        initializer { DetailPemilikViewModel(aplikasiPemilik().container.pemilikRepository) }
    }
}

fun CreationExtras.aplikasiPemilik(): PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropertiApplication)