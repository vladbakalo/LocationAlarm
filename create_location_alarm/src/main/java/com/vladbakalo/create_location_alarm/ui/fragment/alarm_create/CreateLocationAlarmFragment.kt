package com.vladbakalo.create_location_alarm.ui.fragment.alarm_create

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vladbakalo.core.base.BaseVMFragment
import com.vladbakalo.core.common.extentions.injectViewModel
import com.vladbakalo.core.common.helper.validator.ValidatorHelper
import com.vladbakalo.core.common.helper.validator.rules.text.LatitudeRule
import com.vladbakalo.core.common.helper.validator.rules.text.LengthRule
import com.vladbakalo.core.common.helper.validator.rules.text.LongitudeRule
import com.vladbakalo.core.common.helper.validator.rules.text.NotEmptyRule
import com.vladbakalo.core.data.common.LatLng
import com.vladbakalo.core.data.models.AlarmDistance
import com.vladbakalo.core.di.common.ViewModelFactory
import com.vladbakalo.create_location_alarm.R
import com.vladbakalo.create_location_alarm.databinding.FragmentCreateLocationAlarmBinding
import com.vladbakalo.create_location_alarm.ui.CreateLocationAlarmComponentViewModel
import com.vladbakalo.create_location_alarm.ui.fragment.alarm_create.adapter.DistanceAlarmListAdapter
import javax.inject.Inject

class CreateLocationAlarmFragment :BaseVMFragment<CreateLocationAlarmViewModel>(R.layout.fragment_create_location_alarm),
    View.OnClickListener, DistanceAlarmListAdapter.OnAlarmActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val createLocationAlarmComponentViewModel: CreateLocationAlarmComponentViewModel by viewModels()

    private var binding: FragmentCreateLocationAlarmBinding? = null
    private val validator = ValidatorHelper()
    private var alarmDistanceListAdapter: DistanceAlarmListAdapter? = null

    override fun provideViewModel(): CreateLocationAlarmViewModel {
        return injectViewModel(viewModelFactory)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createLocationAlarmComponentViewModel.createLocationAlarmComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState == null){
            if (arguments?.containsKey(KEY_LOCATION_ALARM_ID) == true){
                viewModel.setLocationAlarmId(requireArguments().getLong(KEY_LOCATION_ALARM_ID))
            } else if (arguments?.containsKey(KEY_LOCATION_ALARM_LATITUDE) == true){
                viewModel.setMapPosition(requireArguments().getDouble(KEY_LOCATION_ALARM_LATITUDE),
                    requireArguments().getDouble(KEY_LOCATION_ALARM_LONGITUDE))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateLocationAlarmBinding.bind(view)

        initValidator()
        setUpAdapter()
        observeData()
        setClickListeners()
        setTextChangeListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.create_alarm_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuCreateAlarmItemSave -> onSaveClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initValidator() {
        validator.addEditTextValidator(binding!!.locationAlarmCreateEtNameText, NotEmptyRule(requireContext()),
            LengthRule.newSimpleRule(requireContext()))
        validator.addEditTextValidator(binding!!.locationAlarmCreateEtAddressText, NotEmptyRule(requireContext()),
            LengthRule.newSimpleRule(requireContext()))
        validator.addEditTextValidator(binding!!.locationAlarmCreateEtLatitudeText, NotEmptyRule(requireContext()),
            LatitudeRule(requireContext()))
        validator.addEditTextValidator(binding!!.locationAlarmCreateEtLongitudeText, NotEmptyRule(requireContext()),
            LongitudeRule(requireContext()))
    }

    private fun observeData() {
        viewModel.distanceAlarmListLiveData.observe(viewLifecycleOwner, Observer {
            if (alarmDistanceListAdapter == null) {
                alarmDistanceListAdapter = DistanceAlarmListAdapter(this)
                binding?.locationAlarmCreateRvAlarmList?.adapter = alarmDistanceListAdapter
            }

            alarmDistanceListAdapter!!.setDate(it)
        })
        viewModel.nameLiveData.observe(viewLifecycleOwner, Observer {
            binding?.locationAlarmCreateEtNameText?.setText(it)
        })
        viewModel.addressLiveData.observe(viewLifecycleOwner, Observer {
            binding?.locationAlarmCreateEtAddressText?.setText(it)
        })
        viewModel.latitudeLiveData.observe(viewLifecycleOwner, Observer {
            binding?.locationAlarmCreateEtLatitudeText?.setText(it)
        })
        viewModel.longitudeLiveData.observe(viewLifecycleOwner, Observer {
            binding?.locationAlarmCreateEtLongitudeText?.setText(it)
        })
        viewModel.noteLiveData.observe(viewLifecycleOwner, Observer {
            binding?.locationAlarmCreateEtNoteText?.setText(it)
        })
        viewModel.emptyAlarmDistanceListStateLiveData.observe(viewLifecycleOwner, {
            binding?.locationAlarmCreateTvNoListItemsText?.visibility = if (it) View.VISIBLE else View.GONE
            binding?.locationAlarmCreateRvAlarmList?.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun setClickListeners(){
        binding?.locationAlarmCreateBtnAddAlarm?.setOnClickListener {
            viewModel.onAddAlarmDistanceClick()
        }
    }

    private fun setTextChangeListeners(){
        binding?.locationAlarmCreateEtNameText?.setTextChangeListener{
            viewModel.onNameTextChanged(it)
        }
        binding?.locationAlarmCreateEtAddressText?.setTextChangeListener{
            viewModel.onAddressTextChanged(it)
        }
        binding?.locationAlarmCreateEtLatitudeText?.setTextChangeListener{
            viewModel.onLatitudeTextChanged(it)
        }
        binding?.locationAlarmCreateEtLongitudeText?.setTextChangeListener{
            viewModel.onLongitudeTextChanged(it)
        }
        binding?.locationAlarmCreateEtNoteText?.setTextChangeListener{
            viewModel.onNoteTextChanged(it)
        }
    }

    private fun setUpAdapter() {
        if (alarmDistanceListAdapter == null) {
            alarmDistanceListAdapter =
                DistanceAlarmListAdapter(
                    this
                )
        }
        binding?.locationAlarmCreateRvAlarmList?.adapter = alarmDistanceListAdapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.locationAlarmCreateBtnAddAlarm -> {
                viewModel.onAddAlarmDistanceClick()
                hideKeyboard()
            }
        }
    }

    override fun onRemoveClick(item: AlarmDistance) {
        viewModel.onRemoveAlarmDistanceClick(item)
    }

    override fun onEnabledStateChanged(item: AlarmDistance, enabled: Boolean) {

    }

    private fun onSaveClick(){
        if (validator.validate()) {
            hideKeyboard()
            viewModel.onSaveClick()
        }
    }

    companion object {
        const val TAG = "LocationAlarmCreateFragment"
        private const val KEY_LOCATION_ALARM_ID = "KEY_LOCATION_ALARM_ID"
        private const val KEY_LOCATION_ALARM_LATITUDE = "KEY_LOCATION_ALARM_LATITUDE"
        private const val KEY_LOCATION_ALARM_LONGITUDE = "KEY_LOCATION_ALARM_LONGITUDE"

        fun create(): CreateLocationAlarmFragment {
            return CreateLocationAlarmFragment()
        }

        fun create(locationAlarmId: Long): CreateLocationAlarmFragment {
            val arg = Bundle()
            arg.putLong(KEY_LOCATION_ALARM_ID, locationAlarmId)

            val fragment = CreateLocationAlarmFragment()
            fragment.arguments = arg
            return fragment
        }

        fun create(latLng: LatLng): CreateLocationAlarmFragment {
            val arg = Bundle()
            arg.putDouble(KEY_LOCATION_ALARM_LATITUDE, latLng.latitude)
            arg.putDouble(KEY_LOCATION_ALARM_LONGITUDE, latLng.longitude)

            val fragment = CreateLocationAlarmFragment()
            fragment.arguments = arg
            return fragment
        }
    }
}
