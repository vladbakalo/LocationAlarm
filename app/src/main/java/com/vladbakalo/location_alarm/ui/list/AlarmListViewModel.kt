package com.vladbakalo.location_alarm.ui.list

import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import io.reactivex.schedulers.Schedulers

class AlarmListViewModel(private val interactor: LocationAlarmInteractor) :BaseViewModel() {
    val locationAlarmList: LiveData<List<LocationAlarm>> = interactor.getAllLocationAlarms()

    fun onLocationAlarmEnabledChanged(item: LocationAlarm){
        addDisposable(interactor.changeLocationAlarmEnabledState(item, item.enabled.not())
            .subscribeOn(Schedulers.io())
            .subscribe({ }, {e ->
                onBaseError(e, TAG)
            }))
    }

    fun onLocationAlarmDelete(item: LocationAlarm){
        addDisposable(interactor.deleteLocationAlarm(item.id)
            .subscribeOn(Schedulers.io())
            .subscribe({ }, {e ->
                onBaseError(e, TAG)
            }))
    }

    companion object{
        const val TAG = "AlarmListViewModel"
    }
}