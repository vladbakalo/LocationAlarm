package com.vladbakalo.location_alarm.ui.fragment.alarm_create

import androidx.lifecycle.MutableLiveData
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.common.utils.LocationUtils
import com.vladbakalo.location_alarm.data.ErrorState
import com.vladbakalo.location_alarm.data.models.AlarmDistance
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import com.vladbakalo.location_alarm.navigation.Screens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocationAlarmCreateViewModel @Inject constructor(private val interactor: LocationAlarmInteractor) :
    BaseViewModel() {
    var nameLiveData: MutableLiveData<String> = MutableLiveData("")
    var addressLiveData: MutableLiveData<String> = MutableLiveData("")
    var latitudeLiveData: MutableLiveData<String> = MutableLiveData("")
    var longitudeLiveData: MutableLiveData<String> = MutableLiveData("")
    var noteLiveData: MutableLiveData<String> = MutableLiveData("")
    var distanceAlarmListLiveDistanceData: MutableLiveData<ArrayList<AlarmDistance>> =
        MutableLiveData()
    var enableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var locationAlarmId: Long = 0

    fun setLocationAlarmId(id: Long) {
        locationAlarmId = id

        addDisposable(interactor.getLocationAlarmWithAlarms(locationAlarmId)
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                with(result.locationAlarm) {
                    this@LocationAlarmCreateViewModel.nameLiveData.postValue(name)
                    this@LocationAlarmCreateViewModel.addressLiveData.postValue(address)
                    this@LocationAlarmCreateViewModel.latitudeLiveData.postValue(getLatitudeStr())
                    this@LocationAlarmCreateViewModel.longitudeLiveData.postValue(getLongitudeStr())
                    this@LocationAlarmCreateViewModel.noteLiveData.postValue(note)
                    this@LocationAlarmCreateViewModel.enableLiveData.postValue(enabled)
                }

                val alarmDistanceList = ArrayList(result.alarmDistances)
                distanceAlarmListLiveDistanceData.postValue(alarmDistanceList)
            }, { e -> onBaseError(e, TAG) }))
    }

    fun setMapPosition(latitude: Double, longitude: Double) {
        this.latitudeLiveData.postValue(String.format("%.4f", latitude))
        this.longitudeLiveData.postValue(String.format("%.4f", longitude))

        addDisposable(LocationUtils.getLocationNameRx(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                addressLiveData.postValue(result)
            }, { e -> onBaseError(e, TAG) }))
    }

    fun onAddAlarmClick() {
        val alarmDistanceList = distanceAlarmListLiveDistanceData.value ?: ArrayList()
        router.navigateTo(Screens.AlarmDistanceListScreen(alarmDistanceList))
    }

    fun onSaveClick() {
        if (distanceAlarmListLiveDistanceData.value?.isEmpty() != false) {
            errorStateLiveData.postValue(ErrorState(R.string.add_at_list_one_alarm))
            return
        }

        val locationAlarm =
            LocationAlarm(locationAlarmId, nameLiveData.value!!, addressLiveData.value!!,
                latitudeLiveData.value!!.toDouble(), longitudeLiveData.value!!.toDouble(),
                noteLiveData.value!!, enableLiveData.value ?: true)
        val alarmList = distanceAlarmListLiveDistanceData.value!!

        addDisposable(interactor.createOrUpdateLocationAlarm(locationAlarm, alarmList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loadingStateLiveData.postValue(true)
            }
            .doOnTerminate {
                loadingStateLiveData.postValue(false)
            }
            .subscribe({
                router.exit()
            }, { e ->
                onBaseError(e, TAG)
            }))
    }

    override fun onBackButtonClick(): Boolean {
        router.exit()
        return true
    }

    companion object {
        const val TAG = "LocationAlarmCreateViewModel"
    }
}
