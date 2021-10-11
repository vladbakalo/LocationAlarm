package com.vladbakalo.core.use_case

import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.data.models.AlarmDistance
import com.vladbakalo.core.data.models.LocationAlarm
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetLocationAlarmWithAlarmDistancesUseCase(
    private val locationAlarmRepository: LocationAlarmRepository,
    private val defaultDispatcher: CoroutineDispatcher
) :
    SuspendUseCase<Long, Pair<LocationAlarm, List<AlarmDistance>>>(defaultDispatcher) {

    override suspend fun execute(parameters: Long): Pair<LocationAlarm, List<AlarmDistance>> {
        val locationAlarmWithDistances =
            locationAlarmRepository.getLocationAlarmWithAlarmDistancesById(parameters)
        val locationAlarm = LocationAlarm(locationAlarmWithDistances.locationAlarmDb)
        val alarmDistanceList =
            locationAlarmWithDistances.alarmDistanceList.map { AlarmDistance(it) }

        return Pair(locationAlarm, alarmDistanceList)
    }
}