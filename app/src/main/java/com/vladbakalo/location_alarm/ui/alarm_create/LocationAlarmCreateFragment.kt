package com.vladbakalo.location_alarm.ui.alarm_create

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseVMFragment
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider

class LocationAlarmCreateFragment :BaseVMFragment<LocationAlarmCreateViewModel>() {

    override fun createViewModel(): LocationAlarmCreateViewModel {
        return ViewModelProvider(this).get(LocationAlarmCreateViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.location_alarm_create_fragment, container, false)
    }


    override fun onBackPressed(): Boolean {
        val routerProvider = parentFragment as NavigationRouterProvider

        routerProvider.getRouter().exit()
        return true
    }

    companion object {
        public const val TAG = "LocationAlarmCreateFragment"

        fun create(): LocationAlarmCreateFragment {
            return LocationAlarmCreateFragment()
        }
    }
}
