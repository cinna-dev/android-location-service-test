import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.test_fused_location_provider.DefaultLocationClient
import com.example.test_fused_location_provider.LocationClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.google.android.gms.location.LocationServices

class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
           applicationContext,
           LocationServices.getFusedLocationProviderClient()
        )
    }
}