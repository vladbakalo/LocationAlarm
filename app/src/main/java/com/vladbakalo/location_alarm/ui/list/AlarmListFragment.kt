package com.vladbakalo.location_alarm.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseVMFragment
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.databinding.FragmentAlarmListBinding
import com.vladbakalo.location_alarm.navigation.Screens
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider
import javax.inject.Inject

class AlarmListFragment :BaseVMFragment<AlarmListViewModel>(),
    AlarmListAdapter.Companion.LocationAlarmItemClickListener, View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentAlarmListBinding
    private lateinit var adapter: AlarmListAdapter

    override fun createViewModel(): AlarmListViewModel {
        return ViewModelProvider(this, viewModelFactory)
            .get(AlarmListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAlarmListBinding.inflate(inflater, container, false)
        adapter = AlarmListAdapter(this)

        binding.alarmListRvList.adapter = adapter
        binding.clickListener = this
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi(){
        viewModel?.locationAlarmList?.observe(viewLifecycleOwner, Observer {
            binding.isShowEmptyState = it.isNullOrEmpty()
            adapter.setData(it)
        })
    }

    override fun onEnableButtonClick(item: LocationAlarm) {

    }

    override fun onItemClick(item: LocationAlarm) {

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.alarmListFabAddButton -> openCreateAlarmScreen()
        }
    }

    private fun openCreateAlarmScreen(){
        (parentFragment as NavigationRouterProvider).getRouter()
            .navigateTo(Screens.LocationAlarmCreateScreen)
    }

    companion object {
        const val TAG = "AlarmListFragment"

        fun create(): AlarmListFragment {
            return AlarmListFragment()
        }
    }
}