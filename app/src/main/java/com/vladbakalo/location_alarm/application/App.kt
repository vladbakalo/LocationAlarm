package com.vladbakalo.location_alarm.application

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.vladbakalo.location_alarm.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App :DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .create(this)
    }

    companion object{
        var context: Context? = null
    }
}
