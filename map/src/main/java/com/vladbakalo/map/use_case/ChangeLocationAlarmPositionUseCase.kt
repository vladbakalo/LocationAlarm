package com.vladbakalo.map.use_case

import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.data.common.LatLng
import com.vladbakalo.core.data.models.LocationAlarm
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.CoroutineDispatcher

class ChangeLocationAlarmPositionUseCase constructor(private val locationAlarmRepository: LocationAlarmRepository,
                                                     private val defaultDispatcher: CoroutineDispatcher) :
    SuspendUseCase<Pair<LocationAlarm, LatLng>, Unit>(defaultDispatcher) {

    override suspend fun execute(parameters: Pair<LocationAlarm, LatLng>) {
        val locationAlarm = parameters.first
        with(parameters.second) {
            locationAlarm.latitude = latitude
            locationAlarm.longitude = longitude
        }
        locationAlarmRepository.update(locationAlarm.toDbInstance())
    }
}