package com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list

import androidx.lifecycle.MutableLiveData
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.common.extentions.notify
import com.vladbakalo.location_alarm.data.common.AlarmDistanceData
import com.vladbakalo.location_alarm.data.models.AlarmDistance
import javax.inject.Inject

class AlarmDistanceListViewModel @Inject constructor() :BaseViewModel() {
    val emptyListStateLiveData = MutableLiveData<Boolean>()
    var distanceAlarmListLiveDistanceData: MutableLiveData<MutableList<AlarmDistanceData>> =
        MutableLiveData()

    fun setAlarmDistances(dataList: List<AlarmDistance>) {
        val newDataList = dataList.map { AlarmDistanceData(it) }
            .toMutableList()
        distanceAlarmListLiveDistanceData.postValue(newDataList)
    }

    fun onAddDistanceAlarmClick() {
        val list = distanceAlarmListLiveDistanceData.value ?: ArrayList()
        list.add(AlarmDistanceData())

        distanceAlarmListLiveDistanceData.value = list
    }

    fun onRemoveAlarmDistanceClick(alarmDistanceData: AlarmDistanceData) {
        val alarmList = distanceAlarmListLiveDistanceData.value
        alarmList?.remove(alarmDistanceData)

        distanceAlarmListLiveDistanceData.notify()
        emptyListStateLiveData.postValue(alarmList.isNullOrEmpty())
    }
}