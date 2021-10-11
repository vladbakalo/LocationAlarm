package com.vladbakalo.location_service.di

import android.app.Application
import android.content.Context
import com.vladbakalo.core.common.live_data.LastLocationLiveData
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.location_service.manager.di.ManagerModule
import com.vladbakalo.location_service.service.LocationUpdatesService
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Scope

@LocationServiceScope
@Component(modules = [ManagerModule::class], dependencies = [LocationServiceDeps::class])
interface LocationServiceComponent {

    fun inject(locationUpdatesService: LocationUpdatesService)

    @Component.Builder
    interface Builder {

        fun deps(locationServiceDeps: LocationServiceDeps): Builder

        fun build(): LocationServiceComponent
    }
}

interface LocationServiceDeps {
    fun context(): Context

    fun locationAlarmRepository(): LocationAlarmRepository

    fun lastLocationLiveData(): LastLocationLiveData

    fun coroutineDispatcher(): CoroutineDispatcher
}

interface LocationServiceDepsProvider {
    val locationServiceDeps: LocationServiceDeps
}

@Scope
annotation class LocationServiceScope

val Context.locationServiceDepsProvider: LocationServiceDepsProvider
    get() = when(this){
        is LocationServiceDepsProvider -> this
        is Application -> error("Applications must implement LocationServiceDepsProvider")
        else -> this.applicationContext.locationServiceDepsProvider
    }