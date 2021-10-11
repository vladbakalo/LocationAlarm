package com.vladbakalo.location_alarm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.databinding.FragmentNavigationMapBinding
import com.vladbakalo.location_alarm.ui.base.BaseNavigationRootFragment

class MapRootNavigationFragment: BaseNavigationRootFragment(R.layout.fragment_navigation_map) {

    private var binding: FragmentNavigationMapBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNavigationMapBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun getNavController(): NavController? {
        return binding?.mapRootNavigationFlContainer?.findNavController()
    }

    companion object{
        const val TAG = "MapRootNavigationFragment"
    }
}