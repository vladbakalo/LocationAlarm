package com.vladbakalo.map.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vladbakalo.map.di.DaggerMapComponent
import com.vladbakalo.map.di.MapComponent
import com.vladbakalo.map.di.mapDepsProvider

class MapComponentViewModel(application: Application): AndroidViewModel(application) {

    val mapComponent: MapComponent by lazy {
        DaggerMapComponent.builder().deps(application.mapDepsProvider.mapDeps).build()
    }
}