package com.vladbakalo.location_alarm.di.component

import com.vladbakalo.location_alarm.application.App
import com.vladbakalo.location_alarm.data.di.DatabaseModule
import com.vladbakalo.location_alarm.data.di.RepoModule
import com.vladbakalo.location_alarm.di.builders.ActivityBuilder
import com.vladbakalo.location_alarm.di.builders.ServiceBuilder
import com.vladbakalo.location_alarm.di.module.AppModule
import com.vladbakalo.location_alarm.di.module.AuthModule
import com.vladbakalo.location_alarm.di.module.ManagerModule
import com.vladbakalo.location_alarm.interactor.di.InteractorModule
import com.vladbakalo.location_alarm.navigation.di.NavigationModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DatabaseModule::class,
    RepoModule::class,
    InteractorModule::class,
    NavigationModule::class,
    ActivityBuilder::class,
    ServiceBuilder::class,

    ManagerModule::class,
    AuthModule::class])
interface ApplicationComponent :AndroidInjector<App> {

    @Component.Builder
    abstract class Builder :AndroidInjector.Builder<App>()
}