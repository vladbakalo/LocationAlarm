package com.vladbakalo.location_alarm.application

import android.app.Application
import com.vladbakalo.location_alarm.di.component.ApplicationComponent
import com.vladbakalo.location_alarm.di.component.DaggerApplicationComponent
import com.vladbakalo.location_alarm.di.module.ApplicationModule
import com.vladbakalo.location_alarm.di.module.FragmentModule

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupDI()
    }

    private fun setupDI(){
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        component.inject(this)
    }

    companion object{
        lateinit var component: ApplicationComponent private set
    }
}
