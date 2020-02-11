package com.vladbakalo.location_alarm.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseFragment
import com.vladbakalo.location_alarm.databinding.FragmentSettingsBinding

class SettingsFragment :BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        public const val TAG = "SettingsFragment"

        fun create(): SettingsFragment {
            return SettingsFragment()
        }
    }
}