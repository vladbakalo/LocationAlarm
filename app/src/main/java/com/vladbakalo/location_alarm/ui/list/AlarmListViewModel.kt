package com.vladbakalo.location_alarm.ui.list

import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.data.ErrorState
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class AlarmListViewModel(private val repo: LocationAlarmRepository) :BaseViewModel() {
    val locationAlarmList: LiveData<List<LocationAlarm>> = repo.getAllLocationAlarm()

    fun onLocationAlarmEnabledChanged(item: LocationAlarm){
        item.enabled = item.enabled.not()
        addDisposable(repo.update(item)
            .subscribeOn(Schedulers.io())
            .subscribe({ }, {e ->
                Logger.logException(TAG, e)
                e.message?.let {
                    errorStateLiveData.postValue(ErrorState(it))
                }
            }))
    }

    fun onLocationAlarmDelete(item: LocationAlarm){
        addDisposable(repo.deleteLocationAlarm(item.id)
            .subscribeOn(Schedulers.io())
            .subscribe())
    }

    companion object{
        const val TAG = "AlarmListViewModel"
    }
}