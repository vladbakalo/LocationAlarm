package com.vladbakalo.location_alarm.ui.map

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.common.live_data.LastLocationLiveData
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarms
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import com.vladbakalo.location_alarm.navigation.Screens
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

class AlarmMapViewModel(val interactor: LocationAlarmInteractor,
                        val lastLocationLiveData: LastLocationLiveData) :BaseViewModel() {
    private lateinit var router: Router
    val locationAlarmList: LiveData<List<LocationAlarmWithAlarms>> = interactor.getAllLocationAlarmWithAlarms()

    fun setRouter(router: Router){
        this.router = router
    }

    fun onMapReady(){

    }

    fun onLocationAlarmPositionChanged(locationAlarm: LocationAlarm, position: LatLng){
        addDisposable(interactor.changeLocationAlarmPosition(locationAlarm, position)
            .subscribeOn(Schedulers.io())
            .subscribe())
    }

    fun onCreateLocationAlarmClick(latLng: LatLng){
        firebaseAnalyticsManager.logCreateMap()
        router.navigateTo(Screens.LocationAlarmMapCreateScreen(latLng))
    }

    fun onLocationAlarmMarkerClick(alarm: LocationAlarm) {
        firebaseAnalyticsManager.logEditMap(alarm)
        router.navigateTo(Screens.LocationAlarmEditScreen(alarm.id))
    }
}