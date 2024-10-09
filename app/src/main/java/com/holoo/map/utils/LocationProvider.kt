package com.holoo.map.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import javax.inject.Inject

interface LocationProviderCallback {
    fun onLocationChanged(location: Location?)
}

class LocationProvider @Inject constructor(
    context: Context
) {
    companion object {
        val permissionList = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private var locationProviderCallback: LocationProviderCallback? = null
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest: LocationRequest = LocationRequest.Builder(15*1000)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    var lastLocation: Location? = null
        private set

    private val locationUpdateListener = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            p0.lastLocation?.let {
                lastLocation = it
                locationProviderCallback?.onLocationChanged(lastLocation)
                log("Last Result location was: $lastLocation")
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun setCallback(locationProviderCallback: LocationProviderCallback) {
        this.locationProviderCallback = locationProviderCallback

        fusedLocationClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful) {
                lastLocation = it.result
                locationProviderCallback.onLocationChanged(it.result)
                log("Last registered location was: $lastLocation")
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationUpdateListener,
            Looper.getMainLooper()
        )
    }

    fun removeCallback() {
        log("Removing location updates")
        locationProviderCallback = null
        fusedLocationClient.removeLocationUpdates(locationUpdateListener)
    }

    private fun log(msg: String) {
        Log.d("LocationProvider", msg)
    }
}