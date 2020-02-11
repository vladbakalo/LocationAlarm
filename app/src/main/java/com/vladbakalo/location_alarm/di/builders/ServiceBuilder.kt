package com.vladbakalo.location_alarm.di.builders

import com.vladbakalo.location_alarm.application.service.LocationUpdatesService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilder {

    @ContributesAndroidInjector
    abstract fun contributeLocationUpdatesService(): LocationUpdatesService
}