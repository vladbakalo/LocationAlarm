package com.vladbakalo.create_location_alarm.ui.fragment.alarm_create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vladbakalo.core.base.BaseViewModel
import com.vladbakalo.core.common.ExtendedMutableLiveData
import com.vladbakalo.core.common.extentions.notify
import com.vladbakalo.core.data.ErrorState
import com.vladbakalo.core.data.Result
import com.vladbakalo.core.data.common.LatLng
import com.vladbakalo.core.data.data
import com.vladbakalo.core.data.models.AlarmDistance
import com.vladbakalo.core.data.models.LocationAlarm
import com.vladbakalo.core.data.succeeded
import com.vladbakalo.core.use_case.GetLocationAlarmWithAlarmDistancesUseCase
import com.vladbakalo.create_location_alarm.R
import com.vladbakalo.create_location_alarm.use_case.CreateOrUpdateLocationAlarmUseCase
import com.vladbakalo.create_location_alarm.use_case.GetAddressNameByLocationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateLocationAlarmViewModel @Inject constructor(private val createOrUpdateLocationAlarmUseCase: CreateOrUpdateLocationAlarmUseCase,
                                                       private val getLocationAlarmWithAlarmDistancesUseCase: GetLocationAlarmWithAlarmDistancesUseCase,
                                                       private val getAddressNameByLocationUseCase: GetAddressNameByLocationUseCase,
                                                       private val context: Context) :
    BaseViewModel() {
    private val nameMutableLiveData: ExtendedMutableLiveData<String> = ExtendedMutableLiveData("")
    private val addressMutableLiveData: ExtendedMutableLiveData<String> = ExtendedMutableLiveData("")
    private val latitudeMutableLiveData: ExtendedMutableLiveData<String> = ExtendedMutableLiveData("")
    private val longitudeMutableLiveData: ExtendedMutableLiveData<String> = ExtendedMutableLiveData("")
    private val noteMutableLiveData: ExtendedMutableLiveData<String> = ExtendedMutableLiveData("")
    private val enableMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val distanceAlarmListMutableLiveData: MutableLiveData<ArrayList<AlarmDistance>> =
        MutableLiveData()
    private val emptyAlarmDistanceListStateMutableLiveData: MutableLiveData<Boolean>  = MutableLiveData<Boolean>()

    val nameLiveData: LiveData<String> = nameMutableLiveData
    val addressLiveData: LiveData<String> = addressMutableLiveData
    val latitudeLiveData: LiveData<String> = latitudeMutableLiveData
    val longitudeLiveData: LiveData<String> = longitudeMutableLiveData
    val noteLiveData: LiveData<String> = noteMutableLiveData
    val distanceAlarmListLiveData: LiveData<ArrayList<AlarmDistance>>  = distanceAlarmListMutableLiveData
    val emptyAlarmDistanceListStateLiveData: LiveData<Boolean>  = emptyAlarmDistanceListStateMutableLiveData

    private var locationAlarmId: Long = 0

    fun setLocationAlarmId(id: Long) {
        locationAlarmId = id

        viewModelScope.launch(baseExceptionHandler) {
            val result = getLocationAlarmWithAlarmDistancesUseCase.invoke(locationAlarmId).data

            with(result!!.first) {
                nameMutableLiveData.postValue(name)
                addressMutableLiveData.postValue(address)
                latitudeMutableLiveData.postValue(getLatitudeStr())
                longitudeMutableLiveData.postValue(getLongitudeStr())
                noteMutableLiveData.postValue(note)
                enableMutableLiveData.postValue(enabled)
            }

            val alarmDistanceList = ArrayList(result.second)
            distanceAlarmListMutableLiveData.postValue(alarmDistanceList)
            distanceAlarmListMutableLiveData.postValue(alarmDistanceList)
            emptyAlarmDistanceListStateMutableLiveData
        }
    }

    fun onNameTextChanged(text: String){
        nameMutableLiveData.setValueWithoutNotify(text)
    }

    fun onAddressTextChanged(text: String){
        addressMutableLiveData.setValueWithoutNotify(text)
    }

    fun onLatitudeTextChanged(text: String){
        latitudeMutableLiveData.setValueWithoutNotify(text)
    }

    fun onLongitudeTextChanged(text: String){
        longitudeMutableLiveData.setValueWithoutNotify(text)
    }

    fun onNoteTextChanged(text: String){
        noteMutableLiveData.setValueWithoutNotify(text)
    }

    fun setMapPosition(latitude: Double, longitude: Double) {
        latitudeMutableLiveData.postValue(String.format("%.4f", latitude))
        longitudeMutableLiveData.postValue(String.format("%.4f", longitude))

        viewModelScope.launch(baseExceptionHandler) {
            val params = LatLng(latitude, longitude)
            val addressName = getAddressNameByLocationUseCase.invoke(params)

            addressName.data?.let {
                addressMutableLiveData.postValue(it)
            }
        }
    }

    fun onAddAlarmDistanceClick() {
        val list = distanceAlarmListMutableLiveData.value ?: ArrayList()
        list.add(AlarmDistance())

        distanceAlarmListMutableLiveData.value = list
    }

    fun onSaveClick() {
        if (distanceAlarmListMutableLiveData.value?.isEmpty() != false) {
            errorStateLiveData.postValue(ErrorState(R.string.add_at_list_one_alarm))
            return
        }

        val locationAlarm =
            LocationAlarm(locationAlarmId, nameLiveData.value!!, addressLiveData.value!!,
                latitudeLiveData.value!!.toDouble(), longitudeLiveData.value!!.toDouble(),
                noteLiveData.value!!, enableMutableLiveData.value ?: true)
        val alarmList = distanceAlarmListMutableLiveData.value!!

        viewModelScope.launch(baseExceptionHandler){
            loadingStateLiveData.value = true
            val params = Pair(locationAlarm, alarmList)
            val result = createOrUpdateLocationAlarmUseCase.invoke(params)

            if (!result.succeeded) {
                onBaseError((result as Result.Error).exception)
            }

            loadingStateLiveData.value = false

//            router.exit()
        }
    }

    fun onRemoveAlarmDistanceClick(alarmDistanceData: AlarmDistance) {
        val alarmList = distanceAlarmListMutableLiveData.value
        alarmList?.remove(alarmDistanceData)

        distanceAlarmListMutableLiveData.notify()
        emptyAlarmDistanceListStateMutableLiveData.postValue(alarmList.isNullOrEmpty())
    }

    companion object {
        const val TAG = "LocationAlarmCreateViewModel"
    }
}
