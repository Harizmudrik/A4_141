package com.example.uaspam_141

import android.app.Application
import com.example.uaspam_141.depedenciesinjection.AppContainer
import com.example.uaspam_141.depedenciesinjection.Container

class PropertiApplication:Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}