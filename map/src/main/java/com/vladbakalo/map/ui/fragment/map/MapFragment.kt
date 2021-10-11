package com.vladbakalo.map.ui.fragment.map

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vladbakalo.core.base.BaseVMFragment
import com.vladbakalo.core.common.extentions.injectViewModel
import com.vladbakalo.core.common.utils.PermissionUtils
import com.vladbakalo.core.data.models.LocationAlarm
import com.vladbakalo.core.di.common.ViewModelFactory
import com.vladbakalo.map.R
import com.vladbakalo.map.common.helper.GoogleMapHelper
import com.vladbakalo.map.databinding.FragmentMapBinding
import com.vladbakalo.map.ui.MapComponentViewModel
import javax.inject.Inject

class MapFragment :BaseVMFragment<MapViewModel>(R.layout.fragment_map), OnMapReadyCallback,
    GoogleMapHelper.MapActionListener, View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mapComponentViewModel: MapComponentViewModel by viewModels()

    private var binding: FragmentMapBinding? = null

    private var mapHelper: GoogleMapHelper? = null

    override fun provideViewModel(): MapViewModel {
        return injectViewModel(viewModelFactory)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mapComponentViewModel.mapComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.alarmMapFrMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun observeData(){
        viewModel.locationAlarmListDb.observe(viewLifecycleOwner, {
            mapHelper!!.drawLocationAlarms(it)
        })
        viewModel.lastLocationLiveData.observe(viewLifecycleOwner, Observer {
            mapHelper?.onCurrentLocationReceive(it)
        })
    }

    private fun setClickListeners(){
        binding?.alarmMapFabMyLocationButton?.setOnClickListener {
            mapHelper?.moveToCurrentLocation()
        }
        binding?.alarmMapIvZoomInButton?.setOnClickListener {
            mapHelper?.zoomIn()
        }
        binding?.alarmMapIvZoomOutButton?.setOnClickListener {
            mapHelper?.zoomOut()
        }
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.alarmMapFabMyLocationButton -> mapHelper?.moveToCurrentLocation()
            R.id.alarmMapIvZoomInButton -> mapHelper?.zoomIn()
            R.id.alarmMapIvZoomOutButton -> mapHelper?.zoomOut()
        }
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
            mapHelper = GoogleMapHelper(requireContext(), googleMap, this)
        }

        processLocationPermissionCheck()
        observeData()
        viewModel.onMapReady()
    }

    override fun onMapMarkerDragged(locationAlarm: LocationAlarm, latLng: LatLng) {
        viewModel.onLocationAlarmPositionChanged(locationAlarm, latLng)
    }

    override fun onMapMarkerClick(locationAlarm: LocationAlarm, latLng: LatLng) {
        viewModel.onLocationAlarmMarkerClick(locationAlarm)
    }

    override fun onMapLongClick(latLng: LatLng) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.alarm_creation_question)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.create){
                    _, _ -> viewModel.onCreateLocationAlarmClick(latLng)
            }.show()
    }

    private fun processLocationPermissionCheck(){
        if (PermissionUtils.checkLocationPermission(requireContext())){
            mapHelper?.setMyLocationMarkerEnabled()
        }
    }
    companion object {
        const val TAG = "AlarmMapFragment"

        fun create(): MapFragment {
            return MapFragment()
        }
    }
}