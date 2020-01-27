package com.vladbakalo.location_alarm.di.component

import com.vladbakalo.location_alarm.application.App
import com.vladbakalo.location_alarm.di.module.MainActivityModule
import com.vladbakalo.location_alarm.di.module.AppModule
import com.vladbakalo.location_alarm.di.module.AuthActivityModule
import com.vladbakalo.location_alarm.di.module.AuthModule
import com.vladbakalo.location_alarm.navigation.di.NavigationModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    AuthModule::class,
    NavigationModule::class,
    MainActivityModule::class,
    AuthActivityModule::class])
interface ApplicationComponent: AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}