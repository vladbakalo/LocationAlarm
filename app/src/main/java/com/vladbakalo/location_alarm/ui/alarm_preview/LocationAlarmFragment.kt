package com.vladbakalo.location_alarm.ui.alarm_preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.base.BaseVMFragment

class LocationAlarmFragment :BaseVMFragment<LocationAlarmViewModel>() {

    override fun createViewModel(): LocationAlarmViewModel {
        return LocationAlarmViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        private const val KEY_ALARM_ID = "KEY_ALARM_ID"

        fun create(alarmId: Long): LocationAlarmFragment {
            val fragment = LocationAlarmFragment()

            val argument = Bundle()
            argument.putLong(KEY_ALARM_ID, alarmId)

            fragment.arguments = argument
            return fragment
        }
    }
}