package com.vladbakalo.location_alarm.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.mvp.BaseView

abstract class BaseFragment : Fragment(), BaseView, BackButtonListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    abstract fun injectDependencies()
}
