package com.vladbakalo.location_alarm.ui.fragment.alarm_preview

import android.os.Bundle
import com.vladbakalo.location_alarm.application.base.BaseVMFragment

class LocationAlarmFragment :BaseVMFragment<LocationAlarmViewModel>() {

    override fun provideViewModel(): LocationAlarmViewModel {
        return LocationAlarmViewModel()
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