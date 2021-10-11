package com.vladbakalo.settings.ui.fragment.settings

import com.vladbakalo.core.base.BaseFragment
import com.vladbakalo.settings.R

class SettingsFragment :BaseFragment(R.layout.fragment_settings) {

    companion object {
        const val TAG = "SettingsFragment"

        fun create(): SettingsFragment {
            return SettingsFragment()
        }
    }
}