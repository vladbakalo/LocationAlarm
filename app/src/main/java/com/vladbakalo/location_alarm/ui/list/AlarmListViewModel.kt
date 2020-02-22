package com.vladbakalo.location_alarm.ui.list

import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.data.ErrorState
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.interactor.LocationAlarmCreateEditInteractor
import io.reactivex.schedulers.Schedulers

class AlarmListViewModel(private val interactor: LocationAlarmCreateEditInteractor) :BaseViewModel() {
    val locationAlarmList: LiveData<List<LocationAlarm>> = interactor.getAllLocationAlarms()

    fun onLocationAlarmEnabledChanged(item: LocationAlarm){
        item.enabled = item.enabled.not()
        addDisposable(interactor.updateLocationAlarm(item)
            .subscribeOn(Schedulers.io())
            .subscribe({ }, {e ->
                onError(e, TAG)
            }))
    }

    fun onLocationAlarmDelete(item: LocationAlarm){
        addDisposable(interactor.deleteLocationAlarm(item.id)
            .subscribeOn(Schedulers.io())
            .subscribe({ }, {e ->
                onError(e, TAG)
            }))
    }

    companion object{
        const val TAG = "AlarmListViewModel"
    }
}