package com.vladbakalo.location_alarm.base

import android.content.Context
import android.os.Bundle
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.common.Logger
import dagger.android.support.DaggerFragment


abstract class BaseFragment :DaggerFragment(), BackButtonListener {


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.dt(TAG, "onAttach : $this")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.dt(TAG, "onCreate : $this")
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

    override fun onDestroy() {
        super.onDestroy()
        Logger.dt(TAG, "onDestroy : $this")
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}