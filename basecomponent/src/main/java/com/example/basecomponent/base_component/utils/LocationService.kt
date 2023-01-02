package com.example.basecomponent.base_component.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@SuppressLint("MissingPermission")
// TODO check this later
class LocationService(val application: Application) : LocationListener {

    val MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    var mLocation : Location? = null

    val locationManager  by lazy {
        application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    val criteria by lazy {
        Criteria()
    }

    val bestProvider by lazy {
        locationManager.getBestProvider(criteria, false)
    }


    suspend fun getLatAndLon(): coordinateData? {

        val listener = this

        withContext(Dispatchers.Main){

            // TODO make the more efficient :(
            var provide = LocationManager.NETWORK_PROVIDER
            bestProvider?.let {
                if(bestProvider!!.isNotEmpty()){ provide = bestProvider!!}
            }
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES.toLong(),
                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                listener
            )
        }

        for(i in 0..6){
            if(mLocation != null){
                return coordinateData(mLocation?.latitude!!, mLocation?.longitude!!)
            }

            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)?.let { mLocation ->
                return coordinateData(mLocation?.latitude!!, mLocation?.longitude!!)
            }

            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { mLocation ->
                return coordinateData(mLocation?.latitude!!, mLocation?.longitude!!)
            }
            delay(5000)
        }

        return null
    }


    fun isPermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(application.applicationContext, ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(application.applicationContext, ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    override fun onLocationChanged(location: Location) {
        if(location.latitude == null && location.longitude == null){
            return
        }
        mLocation = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //Todo still do nothing
    }



    data class coordinateData(
        val lat : Double,
        val lon : Double
    )
}