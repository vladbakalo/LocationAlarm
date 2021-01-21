package com.vladbakalo.location_alarm.ui.fragment.list

import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import com.vladbakalo.location_alarm.navigation.Screens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlarmListViewModel @Inject constructor(private val interactor: LocationAlarmInteractor) :
    BaseViewModel() {
    val locationAlarmList: LiveData<List<LocationAlarm>> = interactor.getAllLocationAlarms()

    fun onLocationAlarmClick(item: LocationAlarm) {
        firebaseAnalyticsManager.logEditList(item)
        router.navigateTo(Screens.LocationAlarmEditScreen(item.id))
    }

    fun onAddAlarmClick() {
        firebaseAnalyticsManager.logCreateList()
        router.navigateTo(Screens.LocationAlarmCreateScreen)
    }

    fun onLocationAlarmEnabledChanged(item: LocationAlarm){
        addDisposable(interactor.changeLocationAlarmEnabledState(item, item.enabled.not())
            .subscribeOn(Schedulers.io())
            .subscribe({ }, {e ->
                onBaseError(e, TAG)
            }))
    }

    fun onLocationAlarmDelete(item: LocationAlarm){
        addDisposable(interactor.deleteLocationAlarm(item.id)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({

            }, { e ->
                onBaseError(e, TAG)
            }))
    }

    companion object{
        const val TAG = "AlarmListViewModel"
    }
}