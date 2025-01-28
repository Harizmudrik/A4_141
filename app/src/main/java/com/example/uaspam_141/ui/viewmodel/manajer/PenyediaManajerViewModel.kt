package com.example.uaspam_141.ui.viewmodel.manajer

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam_141.PropertiApplication


object PenyediaManajerViewModel{
    val Factory = viewModelFactory {
        initializer { HomeManajerViewModel(aplikasiManajer().container.manajerRepository) }
        initializer { InsertManajerViewModel(aplikasiManajer().container.manajerRepository) }
        initializer { UpdateManajerViewModel(aplikasiManajer().container.manajerRepository) }
        initializer { DetailManajerViewModel(aplikasiManajer().container.manajerRepository) }
    }
}

fun CreationExtras.aplikasiManajer(): PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropertiApplication)