package com.vladbakalo.location_alarm.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseFragment
import com.vladbakalo.location_alarm.common.utils.ActivityUtils
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

abstract class TabAppNavigator(private val activity: FragmentActivity?,
                               fragmentManager: FragmentManager?,
                               containerId: Int) :
    SupportAppNavigator(activity, fragmentManager, containerId) {

    public var isRoot: Boolean = true

    init {
        fragmentManager?.addOnBackStackChangedListener {
            if (fragmentManager.fragments.isEmpty()) return@addOnBackStackChangedListener

            val fragment = fragmentManager.fragments.first()
            isRoot = fragment is BaseFragment && fragment.isRootScreen
            onCurrentScreenChanged(isRoot)
        }
    }

    override fun setupFragmentTransaction(command: Command?,
                                          currentFragment: Fragment?,
                                          nextFragment: Fragment?,
                                          fragmentTransaction: FragmentTransaction?) {
        if (currentFragment == null){
            return
        }

        fragmentTransaction?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        ActivityUtils.hideKeyboard(activity)
    }

    abstract fun onCurrentScreenChanged(isRoot: Boolean)
}