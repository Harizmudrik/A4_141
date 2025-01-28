package com.example.uaspam_141.ui.viewmodel.properti

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam_141.PropertiApplication
import com.example.uaspam_141.ui.viewmodel.jenis.HomeJenisViewModel
import com.example.uaspam_141.ui.viewmodel.jenis.aplikasiJenis
import com.example.uaspam_141.ui.viewmodel.manajer.HomeManajerViewModel
import com.example.uaspam_141.ui.viewmodel.manajer.aplikasiManajer
import com.example.uaspam_141.ui.viewmodel.pemilik.HomePemilikViewModel
import com.example.uaspam_141.ui.viewmodel.pemilik.aplikasiPemilik


object PenyediaPropertiViewModel{
    val Factory = viewModelFactory {
        initializer { HomePropertiViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { HomeJenisViewModel(aplikasiProperti().container.jenisRepository)}
        initializer { HomePemilikViewModel(aplikasiProperti().container.pemilikRepository)}
        initializer { HomeManajerViewModel(aplikasiProperti().container.manajerRepository) }
        initializer { InsertPropertiViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { UpdatePropertiViewModel(aplikasiProperti().container.propertiRepository) }
        initializer { DetailPropertiViewModel(aplikasiProperti().container.propertiRepository) }
    }
}

fun CreationExtras.aplikasiProperti(): PropertiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropertiApplication)