package com.vladbakalo.map.di

import android.app.Application
import android.content.Context
import com.vladbakalo.core.common.live_data.LastLocationLiveData
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.core.use_case.GetLocationAlarmWithAlarmDistancesListUseCase
import com.vladbakalo.map.di.module.ViewModelModule
import com.vladbakalo.map.ui.fragment.map.MapFragment
import com.vladbakalo.map.use_case.ChangeLocationAlarmPositionUseCase
import com.vladbakalo.map.use_case.UseCaseModule
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Scope

@MapScope
@Component(modules = [ViewModelModule::class, UseCaseModule::class], dependencies = [MapDeps::class])
interface MapComponent {

    fun inject(mapFragment: MapFragment)

    fun changeLocationAlarmPositionUseCase(): ChangeLocationAlarmPositionUseCase

    @Component.Builder
    interface Builder{

        fun deps(deps: MapDeps): Builder

        fun build(): MapComponent
    }
}

interface MapDeps{
    fun context(): Context

    fun coroutineDispatcher(): CoroutineDispatcher

    fun locationAlarmRepository(): LocationAlarmRepository

    fun getLocationAlarmWithAlarmDistancesListUseCase(): GetLocationAlarmWithAlarmDistancesListUseCase

    fun lastLocationLiveData(): LastLocationLiveData
}

interface MapDepsProvider{
    val mapDeps: MapDeps
}

val Context.mapDepsProvider: MapDepsProvider
    get() = when(this) {
        is MapDepsProvider -> this
        is Application -> error("Applications must implement MapDepsProvider")
        else -> this.applicationContext.mapDepsProvider
    }

@Scope
annotation class MapScope