package com.vladbakalo.location_alarm.ui.fragment.alarm_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.extentions.injectViewModel
import com.vladbakalo.location_alarm.di.ViewModelFactory
import javax.inject.Inject

class AlarmListFragment: BaseVMFragment<AlarmListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun provideViewModel(): AlarmListViewModel {
        return injectViewModel(viewModelFactory)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}