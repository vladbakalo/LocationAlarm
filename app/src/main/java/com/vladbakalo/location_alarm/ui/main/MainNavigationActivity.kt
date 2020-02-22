package com.vladbakalo.location_alarm.ui.main

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.service.LocationUpdatesService
import com.vladbakalo.location_alarm.application.base.BaseActivity
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.common.utils.ActivityUtils
import com.vladbakalo.location_alarm.common.utils.IntentUtils
import com.vladbakalo.location_alarm.common.utils.PermissionUtils
import com.vladbakalo.location_alarm.navigation.Screens
import com.vladbakalo.location_alarm.ui.list.AlarmListFragment
import com.vladbakalo.location_alarm.ui.map.AlarmMapFragment
import com.vladbakalo.location_alarm.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainNavigationActivity :BaseActivity() {

    override val navigator = SupportAppNavigator(this, R.id.mainActivityFlContainer)

    private var locationUpdatesService: LocationUpdatesService? = null
    private var isServiceBinned: Boolean = false

    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logger.dt(TAG, "onServiceConnected")
            val binder = service as LocationUpdatesService.LocalBinder
            locationUpdatesService = binder.getService()
            isServiceBinned = true

            if (PermissionUtils.checkLocationPermission(this@MainNavigationActivity)){
                locationUpdatesService?.requestLocationUpdates()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logger.dt(TAG, "onServiceDisconnected")
            locationUpdatesService = null
            isServiceBinned = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainActivityToolbar)
        if (savedInstanceState == null) {
            selectTab(AlarmMapFragment.TAG)
        }

        setListeners()
    }

    override fun getToolBar(): Toolbar {
        return mainActivityToolbar
    }

    override fun onStart() {
        super.onStart()

        checkPermissionAndStartService()
    }

    override fun onStop() {
        super.onStop()
        if (isServiceBinned){
            unbindService(serviceConnection)
            isServiceBinned = false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (PermissionUtils.checkLocationPermission(this)){
            locationUpdatesService?.requestLocationUpdates()
        } else {
            Snackbar.make(mainActivityFlContainer,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings){
                    startActivity(IntentUtils.createAppSettingsIntent())
                }
                .show()
        }
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
        ActivityUtils.hideKeyboard(this)

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

    private fun checkPermissionAndStartService(){
        if (PermissionUtils.checkLocationPermission(this).not()){
            requestLocationPermissions()
        } else {
            LocationUpdatesService.startService(this)

            bindLocationUpdatesService()
        }
    }

    private fun requestLocationPermissions(){
        if (PermissionUtils.shouldShowRequestLocationPermissionRationale(this)){
            Snackbar.make(mainActivityFlContainer,
                R.string.permission_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok){
                    PermissionUtils.requestLocationPermission(this)
                }
                .show()
        } else {
            PermissionUtils.requestLocationPermission(this)
        }
    }

    private fun bindLocationUpdatesService(){
        if (isServiceBinned) return

        bindService(Intent(this, LocationUpdatesService::class.java),
            serviceConnection, 0)
    }

    companion object{
        const val TAG = "MainNavigationActivity"
    }
}
