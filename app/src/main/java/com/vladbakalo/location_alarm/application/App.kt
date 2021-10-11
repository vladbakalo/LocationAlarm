package com.vladbakalo.location_alarm.application

import android.app.Application
import android.content.Context
import com.vladbakalo.core.common.live_data.LastLocationLiveData
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.core.di.CoreComponent
import com.vladbakalo.core.di.CoreDeps
import com.vladbakalo.core.di.DaggerCoreComponent
import com.vladbakalo.core.use_case.*
import com.vladbakalo.create_location_alarm.di.CreateLocationAlarmDeps
import com.vladbakalo.create_location_alarm.di.CreateLocationAlarmDepsProvider
import com.vladbakalo.location_alarm.di.ApplicationComponent
import com.vladbakalo.location_alarm.di.DaggerApplicationComponent
import com.vladbakalo.location_alarm_list.di.LocationAlarmListDeps
import com.vladbakalo.location_alarm_list.di.LocationAlarmListDepsProvider
import com.vladbakalo.location_service.di.LocationServiceDeps
import com.vladbakalo.location_service.di.LocationServiceDepsProvider
import com.vladbakalo.map.di.MapDeps
import com.vladbakalo.map.di.MapDepsProvider
import kotlinx.coroutines.CoroutineDispatcher

class App :Application(), CreateLocationAlarmDepsProvider,
        LocationAlarmListDepsProvider,
        LocationServiceDepsProvider,
        MapDepsProvider {

    lateinit var applicationComponent: ApplicationComponent
    lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .bindContext(this)
            .build()

        coreComponent = DaggerCoreComponent.builder()
            .deps(CoreDepsImpl())
            .build()
    }

    override val createLocationAlarmDeps: CreateLocationAlarmDeps
        get() = this.CreateLocationAlarmDepsImpl()
    override val locationAlarmListDeps: LocationAlarmListDeps
        get() = this.LocationAlarmListDepsImpl()
    override val locationServiceDeps: LocationServiceDeps
        get() = this.LocationServiceDepsImpl()
    override val mapDeps: MapDeps
        get() = this.MapDepsImpl()

    inner class CoreDepsImpl: CoreDeps{
        override val context: Context
            get() = this@App
    }

    inner class CreateLocationAlarmDepsImpl: CreateLocationAlarmDeps{
        override fun context(): Context {
            return applicationComponent.context
        }

        override fun coroutineDispatcher(): CoroutineDispatcher {
            return coreComponent.coroutineDispatcher()
        }

        override fun alarmDistanceRepository(): AlarmDistanceRepository {
            return coreComponent.alarmDistanceRepository()
        }

        override fun locationAlarmRepository(): LocationAlarmRepository {
            return coreComponent.locationAlarmRepository()
        }

        override fun getLocationAlarmWithAlarmDistancesUseCase(): GetLocationAlarmWithAlarmDistancesUseCase {
            return coreComponent.getLocationAlarmWithAlarmDistancesUseCase()
        }

    }

    inner class LocationAlarmListDepsImpl: LocationAlarmListDeps{
        override fun context(): Context {
            return applicationComponent.context
        }

        override fun coroutineDispatcher(): CoroutineDispatcher {
            return coreComponent.coroutineDispatcher()
        }

        override fun getLocationAlarmListUseCae(): GetLocationAlarmListUseCase {
            return coreComponent.getLocationAlarmListUseCase()
        }

        override fun changeLocationAlarmEnabledStateUseCase(): ChangeLocationAlarmEnabledStateUseCase {
            return coreComponent.changeLocationAlarmEnabledStateUseCase()
        }

        override fun deleteLocationAlarmUserCase(): DeleteLocationAlarmUserCase {
            return coreComponent.deleteLocationAlarmUserCase()
        }
    }

    inner class LocationServiceDepsImpl: LocationServiceDeps{
        override fun context(): Context {
            return applicationComponent.context
        }

        override fun locationAlarmRepository(): LocationAlarmRepository {
            return coreComponent.locationAlarmRepository()
        }

        override fun lastLocationLiveData(): LastLocationLiveData {
            return coreComponent.lastLocationLiveData()
        }

        override fun coroutineDispatcher(): CoroutineDispatcher {
            return coreComponent.coroutineDispatcher()
        }
    }

    inner class MapDepsImpl: MapDeps{
        override fun context(): Context {
            return applicationComponent.context
        }

        override fun coroutineDispatcher(): CoroutineDispatcher {
            return coreComponent.coroutineDispatcher()
        }

        override fun locationAlarmRepository(): LocationAlarmRepository {
            return coreComponent.locationAlarmRepository()
        }

        override fun getLocationAlarmWithAlarmDistancesListUseCase(): GetLocationAlarmWithAlarmDistancesListUseCase {
            return coreComponent.getLocationAlarmWithAlarmDistancesListUseCase()
        }

        override fun lastLocationLiveData(): LastLocationLiveData {
            return coreComponent.lastLocationLiveData()
        }
    }
}
