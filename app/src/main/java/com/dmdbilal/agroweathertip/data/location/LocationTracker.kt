package com.dmdbilal.agroweathertip.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.dmdbilal.agroweathertip.presentation.CropViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*

class LocationTracker(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val viewModel: CropViewModel,
    private val context: Context,
    private val activity: Activity
) {
    var lat = 0.0
    var lon = 0.0

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            2
        )
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        if (list != null) {
                            viewModel.setLocation(listOf(list[0].latitude, list[0].longitude))
                            lat = list[0].latitude
                            lon = list[0].longitude

                            println("Coordinates: ${list[0].latitude}, ${list[0].longitude}")
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
}
