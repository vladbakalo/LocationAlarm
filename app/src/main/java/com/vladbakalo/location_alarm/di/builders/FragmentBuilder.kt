package com.vladbakalo.location_alarm.di.builders

import com.vladbakalo.location_alarm.ui.fragment.alarm_create.LocationAlarmCreateFragment
import com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list.AlarmDistanceListFragment
import com.vladbakalo.location_alarm.ui.fragment.list.AlarmListFragment
import com.vladbakalo.location_alarm.ui.fragment.map.AlarmMapFragment
import com.vladbakalo.location_alarm.ui.fragment.tab.TabContainerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeTabContainerFragment(): TabContainerFragment

    @ContributesAndroidInjector
    abstract fun contributeLocationAlarmCreateFragment(): LocationAlarmCreateFragment

    @ContributesAndroidInjector
    abstract fun contributeAlarmListFragment(): AlarmListFragment

    @ContributesAndroidInjector
    abstract fun contributeAlarmMapFragment(): AlarmMapFragment

    @ContributesAndroidInjector
    abstract fun contributeAlarmDistanceListFragment(): AlarmDistanceListFragment
}