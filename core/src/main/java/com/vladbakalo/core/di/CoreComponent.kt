package com.vladbakalo.core.di

import android.app.Application
import android.content.Context
import com.vladbakalo.core.common.live_data.LastLocationLiveData
import com.vladbakalo.core.db.di.DatabaseModule
import com.vladbakalo.core.db.di.RepoModule
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.core.di.module.CoreModule
import com.vladbakalo.core.di.module.UtilsModule
import com.vladbakalo.core.use_case.*
import com.vladbakalo.core.use_case.di.UseCaseModule
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Scope

@CoreScope
@Component(modules = [DatabaseModule::class, CoreModule::class, RepoModule::class, UseCaseModule::class, UtilsModule::class], dependencies = [CoreDeps::class])
interface CoreComponent {

    fun coroutineDispatcher(): CoroutineDispatcher
    fun lastLocationLiveData(): LastLocationLiveData

    fun alarmDistanceRepository(): AlarmDistanceRepository
    fun locationAlarmRepository(): LocationAlarmRepository

    fun getLocationAlarmWithAlarmDistancesUseCase(): GetLocationAlarmWithAlarmDistancesUseCase
    fun getLocationAlarmWithAlarmDistancesListUseCase(): GetLocationAlarmWithAlarmDistancesListUseCase
    fun getLocationAlarmListUseCase(): GetLocationAlarmListUseCase
    fun changeLocationAlarmEnabledStateUseCase(): ChangeLocationAlarmEnabledStateUseCase
    fun deleteLocationAlarmUserCase(): DeleteLocationAlarmUserCase

    @Component.Builder
    interface Builder{

        fun deps(deps: CoreDeps): Builder

        fun build(): CoreComponent
    }
}

interface CoreDeps {
    val context: Context
}

interface CoreDepsProvider {
    val coreDeps: CoreDeps
}

val Context.coreDepsProvider: CoreDepsProvider
    get() = when(this) {
        is CoreDepsProvider -> this
        is Application -> error("Applications must implement CoreDepsProvider")
        else -> this.applicationContext.coreDepsProvider
    }

@Scope
annotation class CoreScope