package com.vladbakalo.location_alarm.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.base.BaseFragment

class SettingsFragment :BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        public const val TAG = "SettingsFragment"

        fun create(): SettingsFragment {
            return SettingsFragment()
        }
    }
}