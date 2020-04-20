package com.vladbakalo.location_alarm.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.extentions.injectViewModel
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.databinding.FragmentAlarmListBinding
import com.vladbakalo.location_alarm.di.ViewModelFactory
import com.vladbakalo.location_alarm.ui.fragment.list.adapter.AlarmListAdapter
import javax.inject.Inject

class AlarmListFragment :BaseVMFragment<AlarmListViewModel>(),
    AlarmListAdapter.LocationAlarmItemClickListener, View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentAlarmListBinding
    private lateinit var adapter: AlarmListAdapter

    override fun provideViewModel(): AlarmListViewModel {
        return injectViewModel(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setRouter(getNavigationRouter())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAlarmListBinding.inflate(inflater, container, false)
        adapter = AlarmListAdapter(this)

        binding.alarmListRvList.adapter = adapter
        binding.clickListener = this

        setListeners()
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi(){
        viewModel.locationAlarmList.observe(viewLifecycleOwner, Observer {
            binding.isShowEmptyState = it.isNullOrEmpty()
            adapter.setData(it)
        })
    }

    private fun setListeners(){
        binding.alarmListRvList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.alarmListFabAddButton.shrink()
                } else {
                    binding.alarmListFabAddButton.extend()
                }
            }
        })
    }

    override fun onEnableButtonClick(item: LocationAlarm) {
        viewModel.onLocationAlarmEnabledChanged(item)
    }

    override fun onItemClick(item: LocationAlarm) {
        viewModel.onLocationAlarmClick(item)
    }

    override fun onLongItemClick(item: LocationAlarm) {
        MaterialAlertDialogBuilder(context)
            .setMessage(R.string.alarm_deletion_question)
            .setPositiveButton(R.string.cancel, null)
            .setNegativeButton(R.string.delete) {
                    _, _ -> viewModel.onLocationAlarmDelete(item)
            }.show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.alarmListFabAddButton -> viewModel.onAddAlarmClick()
        }
    }

    companion object {
        const val TAG = "AlarmListFragment"

        fun create(): AlarmListFragment {
            return AlarmListFragment()
        }
    }
}