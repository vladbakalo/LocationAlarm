package com.vladbakalo.location_alarm.common.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.MyLogger
import com.vladbakalo.location_alarm.common.extentions.toLatLng
import com.vladbakalo.location_alarm.data.models.AlarmDistance
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarmDistances


class GoogleMapHelper(val context: Context,
                      val map: GoogleMap,
                      val listener: MapActionListener) {
    private var locationAlarmMarkers: MutableList<AlarmMarker> = ArrayList()
    private val locationAlarmBitmap = createAlarmBitmap()
    private var isFirstLocationReceive = false
    private var currentLocation: Location? = null

    init {
        initMap()

    }

    private fun initMap(){
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        map.setPadding(0, 0, 0, -50)
        setOnMapListeners()
    }

    private fun setOnMapListeners(){
        map.setOnMapLongClickListener {
            listener.onMapLongClick(it)
        }
        map.setOnMarkerClickListener { marker ->
            val alarm = getAlarmByMarker(marker)
            alarm?.let {
                listener.onMapMarkerClick(it.locationAlarm, marker.position)
            }
            return@setOnMarkerClickListener true
        }
        map.setOnMarkerDragListener(object :GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(marker: Marker?) {
                if (marker == null) return

                val alarm = getAlarmByMarker(marker)
                alarm?.let {
                    logDebug("onDragged", alarm.toString())
                    listener.onMapMarkerDragged(it.locationAlarm, marker.position)
                }
            }

            override fun onMarkerDragStart(marker: Marker?) {}
            override fun onMarkerDrag(marker: Marker?) {}
        })
    }

    fun setMyLocationMarkerEnabled(){
        if (map.isMyLocationEnabled) return

        map.isMyLocationEnabled = true
    }

    fun onCurrentLocationReceive(location: Location){
        currentLocation = location

        if (isFirstLocationReceive) return
        isFirstLocationReceive = true
        val cameraPosition = CameraUpdateFactory.newLatLngZoom(location.toLatLng(), ZOOM_CITY_STREETS)
        map.moveCamera(cameraPosition)
    }

    fun drawLocationAlarms(list: List<LocationAlarmWithAlarmDistances>) {
        logDebug("drawLocationAlarms", "")
        list.forEach {
            logDebug("\n", it.locationAlarm.toString())
        }

        var markerOptions: MarkerOptions
        var marker: Marker
        var alarmMarker: AlarmMarker?

        for (item in list) {
            markerOptions = createAlarmMarkerOption(item.locationAlarm)
            alarmMarker = getExistsAlarmOnMap(item.locationAlarm)
            logDebug("drawMarker", item.toString())

            if (alarmMarker != null){
                logDebug("equalsMarker", "exists on map")
                alarmMarker.alarm = item
                marker = alarmMarker.marker

                if (!equalsMarker(marker, markerOptions)){
                    logDebug("equalsMarker", "update marker position")
                    updateMarkerOptions(marker, markerOptions)
                } else {
                    logDebug("equalsMarker", "marker has same position")
                }
            } else {
                logDebug("drawLocationAlarms", "addMarker")
                marker = map.addMarker(createAlarmMarkerOption(item.locationAlarm))
                alarmMarker = AlarmMarker(item, marker)
                locationAlarmMarkers.add(alarmMarker)
            }

            drawDistanceCircles(item, alarmMarker)
        }

        checkMapAlarmForDeletionByNewAlarmList(list)
    }

    fun moveToCurrentLocation(){
        currentLocation?.let {
            val camera = CameraUpdateFactory.newLatLngZoom(it.toLatLng(), ZOOM_STREETS)
            map.animateCamera(camera)
        }
    }

    fun zoomIn(){
        map.animateCamera(CameraUpdateFactory.zoomIn())
    }

    fun zoomOut(){
        map.animateCamera(CameraUpdateFactory.zoomOut())
    }

    private fun checkMapAlarmForDeletionByNewAlarmList(list: List<LocationAlarmWithAlarmDistances>) {
        locationAlarmMarkers = locationAlarmMarkers.filter {
            if (!ifAlarmExistsInList(it.alarm.locationAlarm, list)) {
                removeAlarmMarker(it)
                return@filter false
            }
            return@filter true
        }
            .toMutableList()
    }

    private fun drawDistanceCircles(locationAlarm: LocationAlarmWithAlarmDistances,
                                    alarmMarker: AlarmMarker) {
        removeCircles(alarmMarker)

        var distanceCircle: Circle
        for (alarm in locationAlarm.alarmDistances) {
            distanceCircle = map.addCircle(
                createAlarmCircleOption(alarm, locationAlarm.locationAlarm.getLatLng()))
            alarmMarker.distanceCircles.add(distanceCircle)
        }
    }

    private fun removeAlarmMarker(alarm: AlarmMarker){
        logDebug("removeAlarmMarker", alarm.toString())
        alarm.marker.remove()
        removeCircles(alarm)
    }

    private fun removeCircles(alarmMarker: AlarmMarker){
        for (circle in alarmMarker.distanceCircles){
            circle.remove()
        }
        alarmMarker.distanceCircles.clear()
    }

    private fun updateMarkerOptions(marker: Marker, markerOptions: MarkerOptions){
        with(markerOptions){
            marker.position = position
            marker.title = title
        }
    }

    private fun equalsMarker(marker: Marker, markerOptions: MarkerOptions): Boolean{
        var isEquals: Boolean
        logDebug("equalsMarker : marker", marker.position.toString())
        logDebug("equalsMarker : option", markerOptions.position.toString())

        with(markerOptions){
            isEquals = marker.position == position
            isEquals = marker.title == title && isEquals
        }
        return isEquals
    }

    private fun getAlarmByMarker(marker: Marker): LocationAlarmWithAlarmDistances? {
        for (entry in locationAlarmMarkers) {
            if (entry.marker == marker) {
                return entry.alarm
            }
        }
        return null
    }

    private fun getExistsAlarmOnMap(alarm: LocationAlarm): AlarmMarker?{
        for (entry in locationAlarmMarkers){
            if (entry.alarm.locationAlarm.id == alarm.id) return entry
        }
        return null
    }

    private fun ifAlarmExistsInList(alarm: LocationAlarm,
                                    alarmList: List<LocationAlarmWithAlarmDistances>): Boolean {
        for (item in alarmList) {
            if (item.locationAlarm.id == alarm.id) return true
        }
        return false
    }

    private fun createAlarmBitmap(): Bitmap{
        val height = LOCATION_ALARM_BITMAP_SIZE
        val width = LOCATION_ALARM_BITMAP_SIZE
        val bitmap = ContextCompat.getDrawable(context, R.drawable.ic_launcher) as BitmapDrawable

        return Bitmap.createScaledBitmap(bitmap.bitmap, width, height, false)
    }

    private fun createAlarmMarkerOption(alarm: LocationAlarm): MarkerOptions {
        return MarkerOptions().position(alarm.getLatLng())
            .title(alarm.name)
            .icon(BitmapDescriptorFactory.fromBitmap(locationAlarmBitmap))
            .draggable(true)
    }

    private fun createAlarmCircleOption(alarmDistance: AlarmDistance, center: LatLng): CircleOptions {
        val color =
            if (alarmDistance.enabled) R.color.circle_active_color else R.color.circle_inactive_color
        return CircleOptions().center(center)
            .radius(alarmDistance.notifyDistanceMeters.toDouble())
            .strokeWidth(ALARM_DISTANCE_CIRCLE_STROKE_SIZE)
            .strokeColor(ContextCompat.getColor(context, color))
    }

    private fun logDebug(method: String, message: String) {
        MyLogger.dt(TAG, "$method : $message")
    }

    companion object{
        const val TAG = "GoogleMapHelper"

        private const val LOCATION_ALARM_BITMAP_SIZE = 120

        private const val ZOOM_WORLD = 1f
        private const val ZOOM_LANDMASS = 5f
        private const val ZOOM_CITY = 10f
        private const val ZOOM_CITY_STREETS = 12f
        private const val ZOOM_STREETS = 15f
        private const val ZOOM_BUILDINGS = 20f

        private const val ALARM_DISTANCE_CIRCLE_STROKE_SIZE = 4f
    }

    data class AlarmMarker(var alarm: LocationAlarmWithAlarmDistances,
                           var marker: Marker,
                           var distanceCircles: MutableList<Circle> = ArrayList())

    interface MapActionListener{
        fun onMapMarkerDragged(alarm: LocationAlarm, latLng: LatLng)

        fun onMapMarkerClick(alarm: LocationAlarm, latLng: LatLng)

        fun onMapLongClick(latLng: LatLng)
    }
}