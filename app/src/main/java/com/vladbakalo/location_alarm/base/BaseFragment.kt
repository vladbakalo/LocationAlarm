package com.vladbakalo.location_alarm.base

import android.os.Bundle
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.common.Logger
import dagger.android.support.DaggerFragment


abstract class BaseFragment :DaggerFragment(), BackButtonListener {

    private var isNeedShowBackButton: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.dt(TAG, "onCreate : $this")
    }

    override fun onStart() {
        super.onStart()
        Logger.dt(TAG, "onStart : $this")
        (activity as BaseActivity).setShowBackButton(isNeedShowBackButton)
    }

    override fun onStop() {
        super.onStop()
        Logger.dt(TAG, "onStop : $this")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.dt(TAG, "onDetach : $this")
    }


    /*
        Call before onStart
     */
    fun setShowBackButton() {
        isNeedShowBackButton = true
    }


    override fun onBackPressed(): Boolean {
        return false
    }

    fun enableBackButton(){

    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}