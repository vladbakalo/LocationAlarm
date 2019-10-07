package com.vladbakalo.location_alarm.application

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.vladbakalo.location_alarm.di.component.ApplicationComponent
import com.vladbakalo.location_alarm.di.component.DaggerApplicationComponent
import com.vladbakalo.location_alarm.di.module.ApplicationModule
import io.fabric.sdk.android.Fabric

class App : Application() {
    companion object{
        lateinit var appComponent: ApplicationComponent private set
    }

    override fun onCreate() {
        super.onCreate()

        initDaggerDI()
        initFabric()
    }

    private fun initDaggerDI(){
        appComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private fun initFabric(){
        Fabric.with(this, Crashlytics())
    }
}
