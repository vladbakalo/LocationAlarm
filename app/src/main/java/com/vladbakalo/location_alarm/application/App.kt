package com.vladbakalo.location_alarm.application

import com.crashlytics.android.Crashlytics
import com.vladbakalo.location_alarm.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric

class App :DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        initFabric()
    }

    private fun initFabric() {
        Fabric.with(this, Crashlytics())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .create(this)
    }
}
