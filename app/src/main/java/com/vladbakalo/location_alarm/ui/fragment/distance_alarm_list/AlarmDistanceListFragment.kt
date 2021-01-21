package com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.extentions.injectViewModel
import com.vladbakalo.location_alarm.data.common.AlarmDistanceData
import com.vladbakalo.location_alarm.data.models.AlarmDistance
import com.vladbakalo.location_alarm.databinding.FragmentAlarmDistanceListBinding
import com.vladbakalo.location_alarm.di.ViewModelFactory
import com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list.adapter.DistanceAlarmListAdapter
import javax.inject.Inject

class AlarmDistanceListFragment :BaseVMFragment<AlarmDistanceListViewModel>(),
    DistanceAlarmListAdapter.OnAlarmActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentAlarmDistanceListBinding

    private var distanceAlarmListAdapter: DistanceAlarmListAdapter? = null

    override fun provideViewModel(): AlarmDistanceListViewModel {
        return injectViewModel(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            viewModel.setAlarmDistances(
                arguments!!.getParcelableArrayList(KEY_LOCATION_ALARM_DISTANCES) ?: ArrayList())
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAlarmDistanceListBinding.inflate(inflater, container, false)
        binding.model = viewModel

        setUpAdapter()
        observeData()
        return binding.root
    }

    private fun observeData() {
        viewModel.distanceAlarmListLiveDistanceData.observe(viewLifecycleOwner, Observer {
            distanceAlarmListAdapter!!.setDate(it)
        })
    }

    private fun setUpAdapter() {
        if (distanceAlarmListAdapter == null) {
            distanceAlarmListAdapter = DistanceAlarmListAdapter(this)
        }
        binding.alarmDistanceListRvAlarmList.adapter = distanceAlarmListAdapter
    }

    override fun onRemoveClick(item: AlarmDistanceData) {
        viewModel.onRemoveAlarmDistanceClick(item)
    }

    companion object {
        const val TAG = "AlarmDistanceListFragment"
        private const val KEY_LOCATION_ALARM_DISTANCES = "KEY_LOCATION_ALARM_DISTANCES"

        fun create(distanceList: ArrayList<AlarmDistance>): AlarmDistanceListFragment {
            val arg = Bundle()
            arg.putParcelableArrayList(KEY_LOCATION_ALARM_DISTANCES, distanceList)

            val fragment = AlarmDistanceListFragment()
            fragment.arguments = arg
            return fragment
        }

    }
}