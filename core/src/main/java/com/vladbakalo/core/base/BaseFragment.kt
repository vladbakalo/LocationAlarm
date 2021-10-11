package com.vladbakalo.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.vladbakalo.core.common.MyLogger
import com.vladbakalo.core.common.utils.ActivityUtils

abstract class BaseFragment (@LayoutRes contentLayoutId: Int) :Fragment(contentLayoutId) {

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

    fun hideKeyboard(){
        ActivityUtils.hideKeyboard(activity)
    }

    companion object {
        private const val TAG = "BaseFragment"

        const val KEY_IS_ROOT_SCREEN = "KEY_IS_ROOT_SCREEN"
    }
}