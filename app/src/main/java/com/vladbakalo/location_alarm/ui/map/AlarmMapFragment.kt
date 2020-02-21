package com.vladbakalo.location_alarm.ui.map

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseVMFragment
import com.vladbakalo.location_alarm.common.helper.GoogleMapHelper

class AlarmMapFragment :BaseVMFragment<AlarmMapViewModel>(), OnMapReadyCallback {

    private lateinit var mapHelper: GoogleMapHelper

    override fun createViewModel(): AlarmMapViewModel {
        return ViewModelProvider(this).get(AlarmMapViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        mapHelper = GoogleMapHelper(
            googleMap)
    }

    companion object {
        public const val TAG = "AlarmMapFragment"

        fun create(): AlarmMapFragment {
            return AlarmMapFragment()
        }
    }
}