package com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list

import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.extentions.injectViewModel
import com.vladbakalo.location_alarm.di.ViewModelFactory
import javax.inject.Inject

class DistanceAlarmListFragment :BaseVMFragment<DistanceAlarmListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun provideViewModel(): DistanceAlarmListViewModel {
        return injectViewModel(viewModelFactory)
    }

}