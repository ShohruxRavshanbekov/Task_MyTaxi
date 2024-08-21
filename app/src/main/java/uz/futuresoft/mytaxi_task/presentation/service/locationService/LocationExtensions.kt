package uz.futuresoft.mytaxi_task.presentation.service.locationService

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

fun Context.hasLocationPermission(): Boolean {
    val fineLocationPermission =
        PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    val coarseLocationPermission =
        PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
    val permissionGranted = PermissionChecker.PERMISSION_GRANTED

    return fineLocationPermission == permissionGranted && coarseLocationPermission == permissionGranted
}