package com.example.test_fused_location_provider

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.test_fused_location_provider.services.MyBackgroundService
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    private val LOCATION_REQUEST_CODE = 10001

    private val TAG = "MainActivity"

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // foreground location
        findViewById<Button>(R.id.btn_get_foreground_location).setOnClickListener {

            Log.d(TAG, "click foreground")

            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getLastLocation()
            } else {
                askLocationPermission()
            }
        }

        findViewById<Button>(R.id.btn_get_background_location).setOnClickListener {
            Log.d(TAG, "click background")

            Intent(this, MyBackgroundService::class.java).also {
                startService(it)
            }
        }
    }

    private fun askLocationPermission() {
        Log.d(TAG, "askLocationPermission")
        // permission for fine location is not granted
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Log.d(TAG, "askLocationPermission: you should ask for an alert dialog...")

                // request ui for fine location  permission
                ActivityCompat.requestPermissions(this,  Array(1) {
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                }, LOCATION_REQUEST_CODE)

            } else {
                ActivityCompat.requestPermissions(this,  Array(1) {
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                }, LOCATION_REQUEST_CODE)
            }
        }
    }

    fun getLastLocationClient(context: Context): Task<Location> {
        val client = getFusedLocationProviderClient(context)
        return GoogleApiAvailability.getInstance()
            .checkApiAvailability(client)
            .onSuccessTask { _ -> client.lastLocation }
            .addOnFailureListener { _ -> Log.d(TAG, "Location unavailable.")}
    }

    private fun getLastLocation() {

           Log.d(TAG, "getLastLocation")
//               fusedLocationClient.lastLocation
       getLastLocationClient(this)
           ?.addOnSuccessListener { location: Location? ->
           if (location == null)  Log.d(TAG, "onSuccess: Location was null...")

           location?.let {
               Log.d(TAG, "onSuccess: $location")
               Log.d(TAG, "onSuccess: ${location.latitude}")
               Log.d(TAG, "onSuccess: ${location.longitude}")
           }
       }
       ?.addOnFailureListener { e ->
           Log.e( TAG, "onFailure: " + e.localizedMessage)
       }

    }
}
