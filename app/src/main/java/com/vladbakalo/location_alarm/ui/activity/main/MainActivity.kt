package com.vladbakalo.location_alarm.ui.activity.main

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import com.vladbakalo.core.base.BaseActivity
import com.vladbakalo.core.common.MyLogger
import com.vladbakalo.core.common.utils.ActivityUtils
import com.vladbakalo.core.common.utils.IntentUtils
import com.vladbakalo.core.common.utils.PermissionUtils
import com.vladbakalo.core.navigation.NavControllerProvider
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.databinding.ActivityMainBinding
import com.vladbakalo.location_alarm.ui.fragment.LocationAlarmListRootNavigationFragment
import com.vladbakalo.location_alarm.ui.fragment.MapRootNavigationFragment
import com.vladbakalo.location_service.service.LocationUpdatesService
import com.vladbakalo.settings.ui.fragment.settings.SettingsFragment

class MainActivity :BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private var locationUpdatesService: LocationUpdatesService? = null
    private var isServiceBinned: Boolean = false

    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            MyLogger.dt(TAG, "onServiceConnected")
            val binder = service as LocationUpdatesService.LocalBinder
            locationUpdatesService = binder.getService()
            isServiceBinned = true

            if (PermissionUtils.checkLocationPermission(this@MainActivity)) {
                locationUpdatesService?.requestLocationUpdates()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            MyLogger.dt(TAG, "onServiceDisconnected")
            locationUpdatesService = null
            isServiceBinned = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainActivityToolbar)
        if (savedInstanceState == null) {
            selectTab(MapRootNavigationFragment.TAG)
        }

        setListeners()
    }

    override fun getToolBar(): Toolbar {
        return binding.mainActivityToolbar
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
        if (!::binding.isInitialized) return

        if (PermissionUtils.checkLocationPermission(this)){
            checkPermissionAndStartService()
        } else {
            Snackbar.make(binding.mainActivityFlContainer,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings){
                    startActivity(IntentUtils.createAppSettingsIntent())
                }
                .show()
        }
        getCurrentFragment(supportFragmentManager)
            ?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun getNavController(): NavController? {
        return (getCurrentFragment(supportFragmentManager) as NavControllerProvider).getNavController()
    }

    override fun onBackPressed() {
        if (getNavController()?.popBackStack() == true){
            return
        }
        if (isCurrentFragmentTagEquals(supportFragmentManager, MapRootNavigationFragment.TAG) == false){
            binding.mainActivityBnvContainer.selectedItemId = R.id.bottomNavigationMenuMap
        }
    }

    private fun setListeners() {
        binding.mainActivityBnvContainer.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomNavigationMenuMap -> selectTab(MapRootNavigationFragment.TAG)
                R.id.bottomNavigationMenuList -> selectTab(LocationAlarmListRootNavigationFragment.TAG)
                R.id.bottomNavigationMenuSettings -> selectTab(SettingsFragment.TAG)
            }

            return@setOnItemSelectedListener true
        }
    }

    private fun selectTab(tag: String) {
        ActivityUtils.hideKeyboard(this)

        val fm = supportFragmentManager
        val currFragment = getCurrentFragment(fm)

        val newFragment = fm.findFragmentByTag(tag)
        if (currFragment != null && newFragment != null && currFragment == newFragment) return

        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(R.id.mainActivityFlContainer, getFragmentByTag(tag), tag)
        }

        if (currFragment != null) {
            transaction.hide(currFragment)
        }

        if (newFragment != null) {
            transaction.show(newFragment)
            invalidateOptionsMenu()
        }
        transaction.commitNow()
    }

    private fun getCurrentFragment(fm: FragmentManager): Fragment?{
        var currFragment: Fragment? = null
        val allFragments = fm.fragments

        for (item in allFragments) {
            if (item.isVisible) {
                currFragment = item
                break
            }
        }
        return currFragment
    }

    private fun isCurrentFragmentTagEquals(fm: FragmentManager, tag: String): Boolean{
        val currFragment = getCurrentFragment(fm)
        return currFragment?.tag == tag
    }

    private fun getFragmentByTag(tag: String): Fragment{
        return when(tag){
            LocationAlarmListRootNavigationFragment.TAG -> LocationAlarmListRootNavigationFragment()
            MapRootNavigationFragment.TAG -> MapRootNavigationFragment()
            else -> throw IllegalArgumentException("Fragment with tag '$tag' not found")
        }
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
            Snackbar.make(binding.mainActivityFlContainer,
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
