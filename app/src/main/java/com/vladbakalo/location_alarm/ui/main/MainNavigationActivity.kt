package com.vladbakalo.location_alarm.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseActivity
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.navigation.Screens
import com.vladbakalo.location_alarm.ui.list.AlarmListFragment
import com.vladbakalo.location_alarm.ui.map.AlarmMapFragment
import com.vladbakalo.location_alarm.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainNavigationActivity :BaseActivity() {

    override val navigator = SupportAppNavigator(this, R.id.mainActivityFlContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            selectTab(AlarmMapFragment.TAG)
        }

        setListeners()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val allFragments = fm.fragments

        for (item in allFragments) {
            if (item.isVisible) {
                fragment = item
                break
            }
        }

        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }

    private fun setListeners() {
        mainActivityBnvContainer.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottomNavigationMenuMap -> selectTab(AlarmMapFragment.TAG)
                R.id.bottomNavigationMenuList -> selectTab(AlarmListFragment.TAG)
                R.id.bottomNavigationMenuSettings -> selectTab(SettingsFragment.TAG)
            }

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun selectTab(tag: String) {
        val fm = supportFragmentManager
        var currFragment: Fragment? = null
        val allFragments = fm.fragments

        for (item in allFragments) {
            if (item.isVisible) {
                currFragment = item
                break
            }
        }

        val newFragment = fm.findFragmentByTag(tag)
        if (currFragment != null && newFragment != null && currFragment == newFragment) return

        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(R.id.mainActivityFlContainer, Screens.TabScreen(tag).fragment, tag)
        }

        if (currFragment != null) {
            transaction.hide(currFragment)
        }

        if (newFragment != null) {
            transaction.show(newFragment)
        }
        transaction.commitNow()
    }
}
