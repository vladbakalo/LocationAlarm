package com.vladbakalo.location_alarm.ui.base

import androidx.annotation.LayoutRes
import androidx.navigation.NavController
import com.vladbakalo.core.base.BaseFragment
import com.vladbakalo.core.navigation.NavControllerProvider

abstract class BaseNavigationRootFragment (@LayoutRes layoutResId: Int): BaseFragment(layoutResId),
    NavControllerProvider {

    private val destinationChangeListener = NavController.OnDestinationChangedListener{ controller, destination, arguments ->

    }

    override fun onResume() {
        super.onResume()
        getNavController()?.addOnDestinationChangedListener(destinationChangeListener)
    }

    override fun onPause() {
        super.onPause()
        getNavController()?.removeOnDestinationChangedListener(destinationChangeListener)
    }
}