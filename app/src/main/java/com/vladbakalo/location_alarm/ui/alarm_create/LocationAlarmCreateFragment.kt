package com.vladbakalo.location_alarm.ui.alarm_create

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.CustomRegex
import com.vladbakalo.location_alarm.common.helper.validator.ValidatorHelper
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.LengthRule
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.NotEmptyRule
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.RegexEditTextRule
import com.vladbakalo.location_alarm.databinding.FragmentLocationAlarmCreateBinding
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider

class LocationAlarmCreateFragment :BaseVMFragment<LocationAlarmCreateViewModel>() {

    private lateinit var binding: FragmentLocationAlarmCreateBinding
    private val validator = ValidatorHelper()

    override fun createViewModel(): LocationAlarmCreateViewModel {
        return ViewModelProvider(this).get(LocationAlarmCreateViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLocationAlarmCreateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        initValidator()
        return binding.root
    }

    private fun initValidator() {
        validator.addEditTextValidator(binding.locationAlarmCreateEtNameText, NotEmptyRule(),
            LengthRule.newSimpleRule())
        validator.addEditTextValidator(binding.locationAlarmCreateEtAddressText, NotEmptyRule(),
            LengthRule.newSimpleRule())
        validator.addEditTextValidator(binding.locationAlarmCreateEtLatitudeText, NotEmptyRule(),
            RegexEditTextRule(CustomRegex.LATITUDE))
        validator.addEditTextValidator(binding.locationAlarmCreateEtLongitudeText, NotEmptyRule(),
            RegexEditTextRule(CustomRegex.LONGITUDE))

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
        val routerProvider = parentFragment as NavigationRouterProvider

        routerProvider.getRouter().exit()
        return true
    }

    private fun onSaveClick(){
        if (validator.validate()){
            Toast.makeText(context, "Valid!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Not Valid!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        public const val TAG = "LocationAlarmCreateFragment"

        fun create(): LocationAlarmCreateFragment {
            return LocationAlarmCreateFragment()
        }
    }
}
