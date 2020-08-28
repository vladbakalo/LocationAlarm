package com.vladbakalo.location_alarm.application.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.common.MyLogger
import com.vladbakalo.location_alarm.common.utils.ActivityUtils
import dagger.android.support.DaggerFragment


abstract class BaseFragment :DaggerFragment(), BackButtonListener {

    var isRootScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyLogger.dt(TAG, "onCreate : $this")
        isRootScreen = arguments?.getBoolean(KEY_IS_ROOT_SCREEN) ?: false
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MyLogger.dt(TAG, "onCreateView : $this")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        MyLogger.dt(TAG, "onStart : $this")
    }

    override fun onStop() {
        super.onStop()
        MyLogger.dt(TAG, "onStop : $this")
    }

    override fun onDetach() {
        super.onDetach()
        MyLogger.dt(TAG, "onDetach : $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyLogger.dt(TAG, "onDestroy : $this")
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    fun hideKeyboard(){
        ActivityUtils.hideKeyboard(activity)
    }

    companion object {
        private const val TAG = "BaseFragment"

        const val KEY_IS_ROOT_SCREEN = "KEY_IS_ROOT_SCREEN"
    }
}