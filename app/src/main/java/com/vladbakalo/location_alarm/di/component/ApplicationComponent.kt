package com.vladbakalo.location_alarm.di.component

import com.vladbakalo.location_alarm.di.module.ActivityModule
import com.vladbakalo.location_alarm.di.module.ApplicationModule
import com.vladbakalo.location_alarm.navigation.di.NavigationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NavigationModule::class])
interface ApplicationComponent {
    fun plusActivityComponent(activityModule: ActivityModule): ActivityComponent
}