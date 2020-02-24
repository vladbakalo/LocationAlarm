package com.vladbakalo.location_alarm.ui.map

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.helper.GoogleMapHelper
import com.vladbakalo.location_alarm.common.utils.PermissionUtils
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import javax.inject.Inject

class AlarmMapFragment :BaseVMFragment<AlarmMapViewModel>(), OnMapReadyCallback,
    GoogleMapHelper.MapActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var mapHelper: GoogleMapHelper? = null

    override fun createViewModel(): AlarmMapViewModel {
        return ViewModelProvider(this, viewModelFactory).get(AlarmMapViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.setRouter(getNavigationRouter())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_alarm_map, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.alarmMapFrMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    private fun observeData(){
        viewModel.locationAlarmList.observe(viewLifecycleOwner, Observer {
            mapHelper!!.drawLocationAlarms(it)
        })
        viewModel.lastLocationLiveData.observe(viewLifecycleOwner, Observer {
            mapHelper?.onCurrentLocationReceive(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.map_settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuMapItemSettings ->
                Toast.makeText(context, "menuMapItemSettings", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        processLocationPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        processLocationPermissionCheck()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap == null) {
            activity?.finish()
            return
        }
        if (mapHelper == null){
            mapHelper = GoogleMapHelper(context!!, googleMap, this)
        }

        processLocationPermissionCheck()
        observeData()
        viewModel.onMapReady()
    }

    override fun onMapMarkerDragged(alarm: LocationAlarm, latLng: LatLng) {
        viewModel.onLocationAlarmPositionChanged(alarm, latLng)
    }

    override fun onMapMarkerClick(alarm: LocationAlarm, latLng: LatLng) {
        Toast.makeText(context, alarm.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMapLongClick(latLng: LatLng) {
        MaterialAlertDialogBuilder(context)
            .setMessage(R.string.alarm_creation_question)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.create){
                    _, _ -> viewModel.onCreateLocationAlarmClick(latLng)
            }.show()
    }

    private fun processLocationPermissionCheck(){
        if (PermissionUtils.checkLocationPermission(context!!)){
            mapHelper?.setMyLocationMarkerEnabled()
        }
    }
    companion object {
        public const val TAG = "AlarmMapFragment"

        fun create(): AlarmMapFragment {
            return AlarmMapFragment()
        }
    }
}