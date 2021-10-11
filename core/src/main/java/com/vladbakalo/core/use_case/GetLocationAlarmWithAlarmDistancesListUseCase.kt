package com.vladbakalo.core.use_case

import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.data.models.AlarmDistance
import com.vladbakalo.core.data.models.LocationAlarm
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetLocationAlarmWithAlarmDistancesListUseCase (private val locationAlarmRepository: LocationAlarmRepository,
                                                                        private val defaultDispatcher: CoroutineDispatcher) :
    SuspendUseCase<Unit?, List<Pair<LocationAlarm, List<AlarmDistance>>>>(defaultDispatcher) {

    override suspend fun execute(parameters: Unit?): List<Pair<LocationAlarm, List<AlarmDistance>>>{
        val formattedList = locationAlarmRepository.getAllLocationAlarmsWithAlarms().map {
            val locationAlarm = LocationAlarm(it.locationAlarmDb)
            val alarmDistanceList = it.alarmDistanceList.map { alarmDistanceDb ->  AlarmDistance(alarmDistanceDb) }
            return@map Pair(locationAlarm, alarmDistanceList)
        }

        return formattedList
    }
}