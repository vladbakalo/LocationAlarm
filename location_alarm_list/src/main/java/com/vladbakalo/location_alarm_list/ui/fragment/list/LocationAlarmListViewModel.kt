package com.vladbakalo.location_alarm_list.ui.fragment.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vladbakalo.core.base.BaseViewModel
import com.vladbakalo.core.data.data
import com.vladbakalo.core.db.models.LocationAlarmDb
import com.vladbakalo.core.navigation.NavigationEvent
import com.vladbakalo.core.use_case.ChangeLocationAlarmEnabledStateUseCase
import com.vladbakalo.core.use_case.DeleteLocationAlarmUserCase
import com.vladbakalo.core.use_case.GetLocationAlarmListUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationAlarmListViewModel @Inject constructor(getLocationAlarmListUseCae: GetLocationAlarmListUseCase,
                                                     private val context: Context,
                                                     private val changeLocationAlarmEnabledStateUseCase: ChangeLocationAlarmEnabledStateUseCase,
                                                     private val deleteLocationAlarmUserCase: DeleteLocationAlarmUserCase) :
    BaseViewModel() {

    private val locationAlarmListMutableLiveData = MutableLiveData<List<LocationAlarmDb>>()
    val locationAlarmListLiveData: LiveData<List<LocationAlarmDb>> =
        locationAlarmListMutableLiveData

    init {
        viewModelScope.launch {
            val result = getLocationAlarmListUseCae.invoke(Unit)
            result.data?.collect {
                locationAlarmListMutableLiveData.value = it
            }
        }
    }

    fun onLocationAlarmClick(item: LocationAlarmDb) {
//        AppFirebaseAnalytics.logEditList(item)
//        router.navigateTo(Screens.LocationAlarmEditScreen(item.id))
    }

    fun onAddAlarmClick() {
//        AppFirebaseAnalytics.logCreateList()
//        router.navigateTo(Screens.LocationAlarmCreateScreen)
        navigationEventSingleLiveData.postValue(NavigationEvent.getCreateLocationAlarmNavigationEvent(context))
    }

    fun onLocationAlarmEnabledChanged(item: LocationAlarmDb, enabled: Boolean) {
        viewModelScope.launch {
            val enableData = Pair(item.id, enabled)
            changeLocationAlarmEnabledStateUseCase.invoke(enableData)
        }
    }

    fun onLocationAlarmDelete(item: LocationAlarmDb) {
        viewModelScope.launch {
            deleteLocationAlarmUserCase.invoke(item.id)
        }
    }

    companion object {
        const val TAG = "AlarmListViewModel"
    }
}