package uz.futuresoft.mytaxi_task.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import uz.futuresoft.mytaxi_task.presentation.service.locationService.utils.LocationServiceConstants.LOCATION_SERVICE_NOTIFICATION_CHANNEL_ID
import uz.futuresoft.mytaxi_task.presentation.service.locationService.utils.LocationServiceConstants.LOCATION_SERVICE_NOTIFICATION_CHANNEL_NAME

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                LOCATION_SERVICE_NOTIFICATION_CHANNEL_ID,
                LOCATION_SERVICE_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}