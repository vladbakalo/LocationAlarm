package com.vladbakalo.location_alarm.ui.alarm_create

import androidx.lifecycle.MutableLiveData
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.common.extentions.notify
import com.vladbakalo.location_alarm.common.utils.LocationUtils
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.data.ErrorState
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import com.vladbakalo.location_alarm.ui.common.AlarmData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

class LocationAlarmCreateViewModel(private val interactor: LocationAlarmInteractor) :BaseViewModel() {
    var name: MutableLiveData<String> = MutableLiveData("")
    var address: MutableLiveData<String> = MutableLiveData("")
    var latitude: MutableLiveData<String> = MutableLiveData("")
    var longitude: MutableLiveData<String> = MutableLiveData("")
    var note: MutableLiveData<String> = MutableLiveData("")
    var alarms: MutableLiveData<MutableList<AlarmData>> = MutableLiveData()
    var enabled: MutableLiveData<Boolean> = MutableLiveData()

    private var locationAlarmId: Long = 0
    private lateinit var router: Router

    fun setLocationAlarmId(id: Long){
        locationAlarmId = id

        addDisposable(interactor.getLocationAlarmWithAlarms(locationAlarmId)
            .subscribeOn(Schedulers.io())
            .subscribe({result ->
                with(result.locationAlarm){
                    this@LocationAlarmCreateViewModel.name.postValue(name)
                    this@LocationAlarmCreateViewModel.address.postValue(address)
                    this@LocationAlarmCreateViewModel.latitude.postValue(getLatitudeStr())
                    this@LocationAlarmCreateViewModel.longitude.postValue(getLongitudeStr())
                    this@LocationAlarmCreateViewModel.note.postValue(note)
                    this@LocationAlarmCreateViewModel.enabled.postValue(enabled)
                }

                alarms.postValue(result.alarms.map { AlarmData(it) }.toMutableList())
            }, {e -> onBaseError(e, TAG)}))
    }

    fun setMapPosition(latitude: Double, longitude: Double){
        this.latitude.postValue(String.format("%.4f", latitude))
        this.longitude.postValue(String.format("%.4f", longitude))

        addDisposable(LocationUtils.getLocationNameRx(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .subscribe({
                    result -> address.postValue(result)
            }, {e -> onBaseError(e, TAG)}))
    }

    fun setRouter(router: Router){
        this.router = router
    }

    fun onAddAlarmClick(){
        val list = alarms.value ?: ArrayList()
        list.add(AlarmData())

        alarms.value = list
    }

    fun onRemoveAlarmClick(alarmData: AlarmData){
        val alarmList = alarms.value
        alarmList?.remove(alarmData)

        alarms.notify()
    }

    fun onSaveClick(){
        if (alarms.value?.isEmpty() != false){
            errorStateLiveData.postValue(ErrorState(R.string.add_at_list_one_alarm))
            return
        }

        val locationAlarm = LocationAlarm(locationAlarmId,
            name.value!!,
            address.value!!,
            latitude.value!!.toDouble(),
            longitude.value!!.toDouble(),
            note.value!!,
            enabled.value ?: true)
        val alarmList = alarms.value!!.map { it.toAlarmModel() }

        addDisposable(interactor.createOrUpdateLocationAlarm(locationAlarm, alarmList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingStateLiveData.postValue(true) }
            .doOnTerminate { loadingStateLiveData.postValue(false) }
            .subscribe ({
                router.exit()
            }, {e ->
                onBaseError(e, TAG)
            }))
    }

    override fun onBackButtonClick(): Boolean{
        router.exit()
        return true
    }

    companion object{
        const val TAG = "LocationAlarmCreateViewModel"
    }
}
