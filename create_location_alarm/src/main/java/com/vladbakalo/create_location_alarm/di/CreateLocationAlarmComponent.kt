package com.vladbakalo.create_location_alarm.di

import android.app.Application
import android.content.Context
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.core.use_case.GetLocationAlarmWithAlarmDistancesUseCase
import com.vladbakalo.create_location_alarm.di.module.ViewModelModule
import com.vladbakalo.create_location_alarm.ui.fragment.alarm_create.CreateLocationAlarmFragment
import com.vladbakalo.create_location_alarm.use_case.di.UseCaseModule
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Scope

@CreateLocationAlarmScope
@Component(modules = [ViewModelModule::class, UseCaseModule::class],
    dependencies = [CreateLocationAlarmDeps::class])
internal interface CreateLocationAlarmComponent {

    fun inject(createLocationAlarmFragment: CreateLocationAlarmFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: CreateLocationAlarmDeps): Builder

        fun build(): CreateLocationAlarmComponent
    }
}

interface CreateLocationAlarmDeps {

    fun context(): Context

    fun coroutineDispatcher(): CoroutineDispatcher

    fun alarmDistanceRepository(): AlarmDistanceRepository

    fun locationAlarmRepository(): LocationAlarmRepository

    fun getLocationAlarmWithAlarmDistancesUseCase(): GetLocationAlarmWithAlarmDistancesUseCase
}

interface CreateLocationAlarmDepsProvider{
    val createLocationAlarmDeps: CreateLocationAlarmDeps
}

val Context.createLocationAlarmDepsProvider: CreateLocationAlarmDepsProvider
    get() = when(this) {
        is CreateLocationAlarmDepsProvider -> this
        is Application -> error("Applications must implement CreateLocationAlarmDepsProvider")
        else -> this.applicationContext.createLocationAlarmDepsProvider
    }

@Scope
internal annotation class CreateLocationAlarmScope