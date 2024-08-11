package uz.futuresoft.mytaxi_task.presentation.service.locationService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import uz.futuresoft.mytaxi_task.domain.model.Location
import uz.futuresoft.mytaxi_task.domain.repository.LocationRepository
import uz.futuresoft.mytaxi_task.presentation.service.locationService.client.DefaultLocationClient
import uz.futuresoft.mytaxi_task.presentation.service.locationService.client.LocationClient
import uz.futuresoft.mytaxi_task.presentation.service.locationService.utils.LocationServiceConstants
import uz.futuresoft.mytaxi_task.presentation.service.locationService.utils.LocationServiceConstants.LOCATION_SERVICE_NOTIFICATION_CHANNEL_ID
import javax.inject.Inject

class LocationService : Service() {

    @Inject
    lateinit var locationRepository: LocationRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            context = applicationContext,
            client = LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTracking()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        stopSelf()
    }

    private fun startTracking() {
        val notification = NotificationCompat.Builder(
            this,
            LOCATION_SERVICE_NOTIFICATION_CHANNEL_ID
        )
            .setContentTitle("Joylashuvni kuzatish")
            .setOngoing(true)
            .build()

        locationClient
            .getLocationUpdates(1_000L)
            .catch { e -> e.printStackTrace() }
            .onEach {
                locationRepository.insertLocation(
                    location = Location(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                )
            }

        startForeground(1, notification)
    }
}