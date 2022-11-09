package com.example.test_fused_location_provider.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

/* intentService is running in a separate thread automatically*/
class MyForegroundService : Service() {
    val TAG = "MyNavigationService"

    init {
        Log.d(TAG, "Service is running...")
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null
}
