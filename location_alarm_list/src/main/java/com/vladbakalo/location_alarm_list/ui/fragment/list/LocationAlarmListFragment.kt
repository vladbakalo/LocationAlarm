package com.vladbakalo.location_alarm_list.ui.fragment.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vladbakalo.core.base.BaseVMFragment
import com.vladbakalo.core.common.extentions.injectViewModel
import com.vladbakalo.core.db.models.LocationAlarmDb
import com.vladbakalo.core.di.common.ViewModelFactory
import com.vladbakalo.location_alarm_list.R
import com.vladbakalo.location_alarm_list.databinding.FragmentLocationAlarmListBinding
import com.vladbakalo.location_alarm_list.ui.LocationAlarmListComponentViewModel
import com.vladbakalo.location_alarm_list.ui.fragment.list.adapter.AlarmListAdapter
import javax.inject.Inject

class LocationAlarmListFragment :BaseVMFragment<LocationAlarmListViewModel>(R.layout.fragment_location_alarm_list),
    AlarmListAdapter.LocationAlarmItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val locationAlarmListComponentViewModel: LocationAlarmListComponentViewModel by viewModels()

    private var binding: FragmentLocationAlarmListBinding? = null
    private var alarmListAdapter: AlarmListAdapter? = null

    override fun provideViewModel(): LocationAlarmListViewModel {
        return injectViewModel(viewModelFactory)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationAlarmListComponentViewModel.locationAlarmListComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationAlarmListBinding.bind(view)

        setUpAdapter()
        setListeners()
        setClickListeners()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        alarmListAdapter = null
    }

    private fun observeData(){
        viewModel.locationAlarmListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                binding?.alarmListRvList?.visibility = View.GONE
                binding?.alarmListClNoItemsView?.visibility = View.VISIBLE
            } else {
                binding?.alarmListRvList?.visibility = View.VISIBLE
                binding?.alarmListClNoItemsView?.visibility = View.GONE
            }
            alarmListAdapter?.setData(it)
        })
    }

    private fun setClickListeners(){
        binding?.alarmListFabAddButton?.setOnClickListener {
            viewModel.onAddAlarmClick()
        }
    }

    private fun setListeners(){
        binding?.alarmListRvList?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding?.alarmListFabAddButton?.shrink()
                } else {
                    binding?.alarmListFabAddButton?.extend()
                }
            }
        })
    }

    private fun setUpAdapter(){
        binding?.alarmListRvList?.adapter = alarmListAdapter
    }

    override fun onEnableButtonClick(item: LocationAlarmDb, enabled: Boolean) {
        viewModel.onLocationAlarmEnabledChanged(item, enabled)
    }

    override fun onItemClick(item: LocationAlarmDb) {
        viewModel.onLocationAlarmClick(item)
    }

    override fun onLongItemClick(item: LocationAlarmDb) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.alarm_deletion_question)
            .setPositiveButton(R.string.cancel, null)
            .setNegativeButton(R.string.delete) {
                    _, _ -> viewModel.onLocationAlarmDelete(item)
            }.show()
    }

    companion object {
        const val TAG = "AlarmListFragment"

    }
}