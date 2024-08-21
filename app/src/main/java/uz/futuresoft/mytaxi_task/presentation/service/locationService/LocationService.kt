package uz.futuresoft.mytaxi_task.presentation.service.locationService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.futuresoft.mytaxi_task.R
import uz.futuresoft.mytaxi_task.domain.model.Location
import uz.futuresoft.mytaxi_task.domain.repository.LocationRepository
import uz.futuresoft.mytaxi_task.domain.useCase.GetLocationUseCase
import uz.futuresoft.mytaxi_task.domain.useCase.InsertLocationUseCase
import uz.futuresoft.mytaxi_task.presentation.service.locationService.client.DefaultLocationClient
import uz.futuresoft.mytaxi_task.presentation.service.locationService.client.LocationClient
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class LocationService : Service() {

    companion object {
        const val LOCATION_SERVICE_NOTIFICATION_CHANNEL_ID = "location"
        const val LOCATION_SERVICE_NOTIFICATION_CHANNEL_NAME = "Location"
    }

    @Inject
    lateinit var insertLocationUseCase: InsertLocationUseCase

    @Inject
    lateinit var getLocationUseCase: GetLocationUseCase

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
        return START_STICKY
    }

    private fun startTracking() {
        val notification =
            NotificationCompat.Builder(this, LOCATION_SERVICE_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_location_on)
                .setContentTitle("Joylashuv kuzatilmoqda")
                .build()

        locationClient
            .getLocationUpdates(1000L)
            .catch { e -> e.printStackTrace() }
            .onEach {
                insertLocationUseCase.invoke(
                    location = Location(
                        latitude = it.latitude,
                        longitude = it.longitude,
                        bearing = it.bearing.toDouble()
                    )
                )
                sendLocationUpdates()
            }
            .launchIn(serviceScope)

        startForeground(1, notification)
    }

    private fun sendLocationUpdates() {
        val intent = Intent("liveLocation")
        intent.putExtra("location", "locationUpdated")
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        serviceScope.cancel()
        stopSelf()
    }
}