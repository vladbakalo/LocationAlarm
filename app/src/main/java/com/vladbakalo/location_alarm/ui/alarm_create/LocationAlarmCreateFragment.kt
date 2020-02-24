package com.vladbakalo.location_alarm.ui.alarm_create

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.common.helper.validator.ValidatorHelper
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.*
import com.vladbakalo.location_alarm.databinding.FragmentLocationAlarmCreateBinding
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider
import com.vladbakalo.location_alarm.ui.common.AlarmAdapter
import com.vladbakalo.location_alarm.ui.common.AlarmData
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LocationAlarmCreateFragment :BaseVMFragment<LocationAlarmCreateViewModel>(),
    View.OnClickListener, AlarmAdapter.OnAlarmActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentLocationAlarmCreateBinding
    private val validator = ValidatorHelper()
    private var alarmAdapter: AlarmAdapter? = null

    override fun createViewModel(): LocationAlarmCreateViewModel {
        return ViewModelProvider(this, viewModelFactory).get(LocationAlarmCreateViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState == null){
            if (arguments?.containsKey(KEY_LOCATION_ALARM_ID) == true){
                viewModel.setLocationAlarmId(arguments!!.getLong(KEY_LOCATION_ALARM_ID))
            } else if (arguments?.containsKey(KEY_LOCATION_ALARM_LATITUDE) == true){
                viewModel.setMapPosition(arguments!!.getDouble(KEY_LOCATION_ALARM_LATITUDE),
                    arguments!!.getDouble(KEY_LOCATION_ALARM_LONGITUDE))
            }
        }
        viewModel.setRouter(getNavigationRouter())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLocationAlarmCreateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.clickListener = this
        binding.isAlarmListEmpty = true

        initValidator()
        observeData()
        return binding.root
    }

    private fun initValidator() {
        validator.addEditTextValidator(binding.locationAlarmCreateEtNameText, NotEmptyRule(),
            LengthRule.newSimpleRule())
        validator.addEditTextValidator(binding.locationAlarmCreateEtAddressText, NotEmptyRule(),
            LengthRule.newSimpleRule())
        validator.addEditTextValidator(binding.locationAlarmCreateEtLatitudeText, NotEmptyRule(),
            LatitudeRule())
        validator.addEditTextValidator(binding.locationAlarmCreateEtLongitudeText, NotEmptyRule(),
            LongitudeRule())
    }

    private fun observeData(){
        viewModel.alarms.observe(viewLifecycleOwner, Observer {
            if (alarmAdapter == null) {
                alarmAdapter = AlarmAdapter(this)
                binding.locationAlarmCreateRvAlarmList.adapter = alarmAdapter
            }

            alarmAdapter!!.setDate(it)
            binding.isAlarmListEmpty = it.isEmpty()
        } )
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

    override fun onBackPressed(): Boolean {
        return viewModel.onBackButtonClick()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.locationAlarmCreateBtnAddAlarm -> {
                viewModel.onAddAlarmClick()
                hideKeyboard()
            }
        }
    }

    private fun onSaveClick(){
        if (validate()){
            hideKeyboard()
            viewModel.onSaveClick()
        }
    }

    override fun onRemoveClick(item: AlarmData) {
        viewModel.onRemoveAlarmClick(item)
    }

    private fun validate(): Boolean{
        var isValid = validator.validate()
        isValid = alarmAdapter?.validate(binding.locationAlarmCreateRvAlarmList) ?: true && isValid

        return isValid
    }

    companion object {
        const val TAG = "LocationAlarmCreateFragment"
        private const val KEY_LOCATION_ALARM_ID = "KEY_LOCATION_ALARM_ID"
        private const val KEY_LOCATION_ALARM_LATITUDE = "KEY_LOCATION_ALARM_LATITUDE"
        private const val KEY_LOCATION_ALARM_LONGITUDE = "KEY_LOCATION_ALARM_LONGITUDE"

        fun create(): LocationAlarmCreateFragment {
            return LocationAlarmCreateFragment()
        }

        fun create(locationAlarmId: Long): LocationAlarmCreateFragment {
            val arg = Bundle()
            arg.putLong(KEY_LOCATION_ALARM_ID, locationAlarmId)

            val fragment = LocationAlarmCreateFragment()
            fragment.arguments = arg
            return fragment
        }

        fun create(latLng: LatLng): LocationAlarmCreateFragment {
            val arg = Bundle()
            arg.putDouble(KEY_LOCATION_ALARM_LATITUDE, latLng.latitude)
            arg.putDouble(KEY_LOCATION_ALARM_LONGITUDE, latLng.longitude)

            val fragment = LocationAlarmCreateFragment()
            fragment.arguments = arg
            return fragment
        }
    }
}
