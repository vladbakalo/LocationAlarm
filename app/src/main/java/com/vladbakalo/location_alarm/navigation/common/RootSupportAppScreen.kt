package com.vladbakalo.location_alarm.navigation.common

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.vladbakalo.location_alarm.base.BaseFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class RootSupportAppScreen :SupportAppScreen() {

    @CallSuper
    override fun getFragment(): Fragment {
        val fragment = getScreenFragment()

        val arg = fragment.arguments ?: Bundle()
        if (fragment is BaseFragment){
            arg.putBoolean(BaseFragment.KEY_IS_ROOT_SCREEN, true)
            fragment.arguments = arg
        }
        return fragment
    }

    abstract fun getScreenFragment(): Fragment
}