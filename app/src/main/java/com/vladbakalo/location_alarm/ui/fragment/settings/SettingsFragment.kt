package com.vladbakalo.location_alarm.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.application.base.BaseFragment
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
        const val TAG = "SettingsFragment"

        fun create(): SettingsFragment {
            return SettingsFragment()
        }
    }
}