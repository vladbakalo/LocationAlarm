package com.vladbakalo.map.ui.fragment.map

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.vladbakalo.core.base.BaseViewModel
import com.vladbakalo.core.common.helper.AppFirebaseAnalytics
import com.vladbakalo.core.common.live_data.LastLocationLiveData
import com.vladbakalo.core.data.data
import com.vladbakalo.core.data.models.AlarmDistance
import com.vladbakalo.core.data.models.LocationAlarm
import com.vladbakalo.core.navigation.NavigationEvent
import com.vladbakalo.core.use_case.GetLocationAlarmWithAlarmDistancesListUseCase
import com.vladbakalo.map.use_case.ChangeLocationAlarmPositionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(private val changeLocationAlarmPositionUseCase: ChangeLocationAlarmPositionUseCase,
                                       private val getLocationAlarmWithAlarmDistancesListUseCase: GetLocationAlarmWithAlarmDistancesListUseCase,
                                       private val context: Context,
                                       val lastLocationLiveData: LastLocationLiveData) :
    BaseViewModel() {
    val locationAlarmListDb = MutableLiveData<List<Pair<LocationAlarm, List<AlarmDistance>>>>()


    init {
        viewModelScope.launch {
            val result = getLocationAlarmWithAlarmDistancesListUseCase.invoke(Unit)
            result.data?.let {
                locationAlarmListDb.postValue(it)
            }
        }
    }

    fun onMapReady() {

    }

    fun onLocationAlarmPositionChanged(locationAlarm: LocationAlarm, position: LatLng) {
        viewModelScope.launch {
            val pos = com.vladbakalo.core.data.common.LatLng(position.latitude, position.longitude)
            changeLocationAlarmPositionUseCase.invoke(Pair(locationAlarm, pos))
        }
    }

    fun onCreateLocationAlarmClick(latLng: LatLng){
        AppFirebaseAnalytics.logCreateMap()
        navigationEventSingleLiveData.postValue(NavigationEvent.getCreateLocationAlarmNavigationEvent(context))
    }

    fun onLocationAlarmMarkerClick(alarmDb: LocationAlarm) {
        AppFirebaseAnalytics.logEditMap(alarmDb)
//        router.navigateTo(Screens.LocationAlarmEditScreen(alarmDb.id))
    }
}