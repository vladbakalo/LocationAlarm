package com.vladbakalo.location_alarm.ui.list

import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.base.BaseViewModel
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository

class AlarmListViewModel(repo: LocationAlarmRepository) :BaseViewModel() {

    public val locationAlarmList: LiveData<List<LocationAlarm>> = repo.getAllLocationAlarm()

}