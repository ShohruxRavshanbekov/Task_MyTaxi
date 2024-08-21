@file:OptIn(MapboxExperimental::class)

package uz.futuresoft.mytaxi_task.presentation.mainActivity

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.mytaxi_task.R
import uz.futuresoft.mytaxi_task.presentation.components.IconButton
import uz.futuresoft.mytaxi_task.presentation.components.StateSwitch
import uz.futuresoft.mytaxi_task.presentation.components.TextButton
import uz.futuresoft.mytaxi_task.presentation.components.VerticalSpace
import uz.futuresoft.mytaxi_task.presentation.components.bottomSheet.BottomSheet
import uz.futuresoft.mytaxi_task.presentation.mainActivity.MainActivity.Companion.DEFAULT_ZOOM
import uz.futuresoft.mytaxi_task.presentation.mainActivity.event.GetLocationEvent
import uz.futuresoft.mytaxi_task.presentation.mainActivity.state.MainActivityState
import uz.futuresoft.mytaxi_task.presentation.service.locationService.LocationService
import uz.futuresoft.mytaxi_task.presentation.service.locationService.hasLocationPermission
import uz.futuresoft.mytaxi_task.presentation.ui.theme.Green
import uz.futuresoft.mytaxi_task.presentation.ui.theme.MyTaxiTaskTheme
import uz.futuresoft.mytaxi_task.presentation.utils.Constants.MAPBOX_STANDARD_DARK
import uz.futuresoft.mytaxi_task.presentation.utils.Constants.MAPBOX_STANDARD_LIGHT

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val DEFAULT_ZOOM = 16.0
    }

    private val viewModel: MainActivityViewModel by viewModels()
    private val locationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.send(event = GetLocationEvent())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocationPermissions(activity = this)
        val intentFiler = IntentFilter("liveLocation")
        registerReceiver(locationReceiver, intentFiler)
        setContent {
            MyTaxiTaskTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    val mainState by viewModel.state.collectAsState()

                    Content(state = mainState)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(locationReceiver)
    }

    private fun checkLocationPermissions(activity: Activity) {
        if (activity.hasLocationPermission()) {
            startLocationService(activity = this)
        } else {
            locationPermissionRequester.launch(
                arrayOf(FINE_LOCATION_PERMISSION, COARSE_LOCATION_PERMISSION)
            )
        }
    }

    private val locationPermissionRequester =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineLocationPermissionIsGranted = permissions[FINE_LOCATION_PERMISSION] ?: false
            val coarseLocationPermissionIsGranted = permissions[COARSE_LOCATION_PERMISSION] ?: false

            if (fineLocationPermissionIsGranted && coarseLocationPermissionIsGranted) {
                checkLocationPermissions(this)
            }
        }

    private fun startLocationService(activity: Activity) {
        val intent = Intent(activity.applicationContext, LocationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent)
        } else {
            activity.startService(intent)
        }
    }
}

@Composable
fun Content(state: MainActivityState = MainActivityState()) {
    val context = LocalContext.current
    var isSheetExpanded by remember { mutableStateOf(false) }
    val latitude = state.location.latitude
    val longitude = state.location.longitude
    val bearing = state.location.bearing
    val point = Point.fromLngLat(longitude, latitude)
    var zoom by remember { mutableDoubleStateOf(DEFAULT_ZOOM) }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(zoom)
            center(point)
        }
    }

    LaunchedEffect(key1 = state.location) {
        mapViewportState.moveCameraTo(
            latitude = latitude,
            longitude = longitude
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = {
                MapStyle(
                    style = if (isSystemInDarkTheme())
                        MAPBOX_STANDARD_DARK
                    else
                        MAPBOX_STANDARD_LIGHT
                )

            },
            compass = {},
            scaleBar = {},
            logo = {}
        ) {
            PointAnnotationGroup(
                annotations = listOf(point).map {
                    PointAnnotationOptions()
                        .withPoint(point = it)
                        .withIconImage(
                            iconImageBitmap = bitmapFromDrawableRes(
                                context = context,
                                resourceId = R.drawable.ic_taxi_car
                            )!!
                        )
                        .withIconRotate(iconRotate = bearing)
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    cardColor = MaterialTheme.colorScheme.surface,
                    icon = painterResource(id = R.drawable.ic_hamburger_menu),
                    iconTint = MaterialTheme.colorScheme.onPrimary,
                    onClick = {}
                )
                StateSwitch()
                TextButton(
                    cardColor = Green,
                    borderWidth = 4.dp,
                    text = "95",
                    onClick = {}
                )
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                AnimatedVisibility(
                    visible = !isSheetExpanded,
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
                ) {
                    Box {
                        IconButton(
                            cardColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9F),
                            borderWidth = 4.dp,
                            borderColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9F),
                            icon = painterResource(id = R.drawable.ic_chevrons),
                            onClick = {}
                        )
                    }
                }
                AnimatedVisibility(
                    visible = !isSheetExpanded,
                    enter = slideInHorizontally() + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(
                            cardColor = MaterialTheme.colorScheme.surface.copy(0.9F),
                            icon = painterResource(id = R.drawable.ic_plus),
                            onClick = {
                                mapViewportState.moveCameraTo(
                                    latitude = latitude,
                                    longitude = longitude,
                                    zoom = ++zoom
                                )
                            }
                        )
                        VerticalSpace(height = 16.dp)
                        IconButton(
                            cardColor = MaterialTheme.colorScheme.surface.copy(0.9F),
                            icon = painterResource(id = R.drawable.ic_minus),
                            onClick = {
                                mapViewportState.moveCameraTo(
                                    latitude = latitude,
                                    longitude = longitude,
                                    zoom = --zoom
                                )
                            }
                        )
                        VerticalSpace(height = 16.dp)
                        IconButton(
                            cardColor = MaterialTheme.colorScheme.surface.copy(0.9F),
                            icon = painterResource(id = R.drawable.ic_navigation),
                            iconTint = Color.Unspecified,
                            onClick = {
                                mapViewportState.moveCameraTo(
                                    latitude = latitude,
                                    longitude = longitude,
                                )
                            }
                        )
                    }
                }
            }
            BottomSheet(
                isSheetExpanded = isSheetExpanded,
                onVerticalDrag = { _, dragAmount ->
                    when {
                        dragAmount < 0 -> isSheetExpanded = true
                        dragAmount > 0 -> isSheetExpanded = false
                    }
                }
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MainActivityPreview() {
    MyTaxiTaskTheme {
        Content()
    }
}

fun MapViewportState.moveCameraTo(
    latitude: Double,
    longitude: Double,
    zoom: Double = DEFAULT_ZOOM,
) = flyTo(
    cameraOptions = cameraOptions {
        center(Point.fromLngLat(longitude,latitude))
        zoom(zoom)
    },
    animationOptions = MapAnimationOptions.mapAnimationOptions { duration(duration = 800) },
)

private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
    convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
    if (sourceDrawable == null) {
        return null
    }
    return if (sourceDrawable is BitmapDrawable) {
        sourceDrawable.bitmap
    } else {
        val constantState = sourceDrawable.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    }
}