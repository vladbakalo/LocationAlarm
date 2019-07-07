package com.vladbakalo.location_alarm.di.component

import com.vladbakalo.location_alarm.application.BaseApplication
import com.vladbakalo.location_alarm.di.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: BaseApplication)
}