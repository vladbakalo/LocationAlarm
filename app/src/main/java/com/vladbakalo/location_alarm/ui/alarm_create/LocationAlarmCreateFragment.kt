package com.vladbakalo.location_alarm.ui.alarm_create

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseVMFragment
import com.vladbakalo.location_alarm.databinding.FragmentLocationAlarmCreateBinding
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider

class LocationAlarmCreateFragment :BaseVMFragment<LocationAlarmCreateViewModel>() {

    private lateinit var binding: FragmentLocationAlarmCreateBinding

    override fun createViewModel(): LocationAlarmCreateViewModel {
        return ViewModelProvider(this).get(LocationAlarmCreateViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setShowBackButton()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLocationAlarmCreateBinding.inflate(inflater, container, false)

        return binding.root
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
