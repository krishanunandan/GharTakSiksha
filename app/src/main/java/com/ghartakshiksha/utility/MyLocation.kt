package com.ghartakshiksha.utility

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import java.util.*

public class MyLocation {
    var timer1: Timer? = null
    var locationManager: LocationManager? = null
    var locationResult: LocationResult? = null
    var isGPSEnabled = false
    var isNetworkEnabled = false

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, result: LocationResult?): Boolean {
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult = result
        if (locationManager == null) locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //exceptions will be thrown if provider is not permitted.
        try {
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        try {
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        //don't start listeners if no provider is enabled
        if (!isGPSEnabled && !isNetworkEnabled) return false
        if (isGPSEnabled) locationManager!!.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListenerGps
        )
        if (isNetworkEnabled) locationManager!!.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0,
            0f,
            locationListenerNetwork
        )
        timer1 = Timer()
        timer1!!.schedule(GetLastLocation(), 20000)
        return true
    }

    var locationListenerGps: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            timer1!!.cancel()
            locationResult!!.gotLocation(location)
            locationManager!!.removeUpdates(this)
            locationManager!!.removeUpdates(locationListenerNetwork)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }
    var locationListenerNetwork: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            timer1!!.cancel()
            locationResult!!.gotLocation(location)
            locationManager!!.removeUpdates(this)
            locationManager!!.removeUpdates(locationListenerGps)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }

    internal inner class GetLastLocation : TimerTask() {
        @SuppressLint("MissingPermission")
        override fun run() {
            locationManager!!.removeUpdates(locationListenerGps)
            locationManager!!.removeUpdates(locationListenerNetwork)
            var networkLocation: Location? = null
            var gpsLocation: Location? = null
            if (isGPSEnabled) gpsLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (isNetworkEnabled) networkLocation =
                locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            //if there are both values use the latest one
            if (gpsLocation != null && networkLocation != null) {
                if (gpsLocation.time > networkLocation.time) locationResult!!.gotLocation(gpsLocation) else locationResult!!.gotLocation(
                    networkLocation
                )
                return
            }
            if (gpsLocation != null) {
                locationResult!!.gotLocation(gpsLocation)
                return
            }
            if (networkLocation != null) {
                locationResult!!.gotLocation(networkLocation)
                return
            }
            locationResult!!.gotLocation(null)
        }
    }

    public abstract class LocationResult {
        abstract fun gotLocation(location: Location?)
    }
}