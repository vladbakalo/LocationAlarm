package com.vladbakalo.location_alarm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.databinding.FragmentNavigationLocationAlarmListBinding
import com.vladbakalo.location_alarm.ui.base.BaseNavigationRootFragment

class LocationAlarmListRootNavigationFragment: BaseNavigationRootFragment(R.layout.fragment_navigation_location_alarm_list) {

    private var binding: FragmentNavigationLocationAlarmListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNavigationLocationAlarmListBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun getNavController(): NavController? {
        return binding?.locationAlarmListRootNavigationFlContainer?.findNavController()
    }

    companion object{
        const val TAG = "LocationAlarmListRootNavigationFragment"
    }
}