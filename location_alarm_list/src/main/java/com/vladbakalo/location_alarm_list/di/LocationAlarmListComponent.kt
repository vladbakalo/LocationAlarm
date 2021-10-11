package com.vladbakalo.location_alarm_list.di

import android.app.Application
import android.content.Context
import com.vladbakalo.core.use_case.ChangeLocationAlarmEnabledStateUseCase
import com.vladbakalo.core.use_case.DeleteLocationAlarmUserCase
import com.vladbakalo.core.use_case.GetLocationAlarmListUseCase
import com.vladbakalo.location_alarm_list.di.module.ViewModelModule
import com.vladbakalo.location_alarm_list.ui.fragment.list.LocationAlarmListFragment
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Scope

@LocationAlarmListScope
@Component(modules = [ViewModelModule::class],
    dependencies = [LocationAlarmListDeps::class])
internal interface LocationAlarmListComponent {

    fun inject(locationAlarmListFragment: LocationAlarmListFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: LocationAlarmListDeps): Builder

        fun build(): LocationAlarmListComponent
    }
}

interface LocationAlarmListDeps {
    fun context(): Context

    fun coroutineDispatcher(): CoroutineDispatcher

    fun getLocationAlarmListUseCae(): GetLocationAlarmListUseCase

    fun changeLocationAlarmEnabledStateUseCase(): ChangeLocationAlarmEnabledStateUseCase

    fun deleteLocationAlarmUserCase(): DeleteLocationAlarmUserCase
}

interface LocationAlarmListDepsProvider{
    val locationAlarmListDeps: LocationAlarmListDeps
}

val Context.locationAlarmListDepsProvider: LocationAlarmListDepsProvider
    get() = when(this) {
        is LocationAlarmListDepsProvider -> this
        is Application -> error("Applications must implement LocationAlarmListDepsProvide")
        else -> this.applicationContext.locationAlarmListDepsProvider
    }

@Scope
internal annotation class LocationAlarmListScope