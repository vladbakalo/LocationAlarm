package com.vladbakalo.location_alarm.application.base

import android.os.Bundle
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.common.utils.ActivityUtils
import dagger.android.support.DaggerFragment


abstract class BaseFragment :DaggerFragment(), BackButtonListener {

    public var isRootScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.dt(TAG, "onCreate : $this")
        isRootScreen = arguments?.getBoolean(KEY_IS_ROOT_SCREEN) ?: false
    }

    override fun onStart() {
        super.onStart()
        Logger.dt(TAG, "onStart : $this")
    }

    override fun onStop() {
        super.onStop()
        Logger.dt(TAG, "onStop : $this")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.dt(TAG, "onDetach : $this")
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    fun hideKeyboard(){
        ActivityUtils.hideKeyboard(activity)
    }

    companion object {
        private const val TAG = "BaseFragment"

        public const val KEY_IS_ROOT_SCREEN = "KEY_IS_ROOT_SCREEN"
    }
}