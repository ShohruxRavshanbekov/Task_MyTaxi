@file:OptIn(ExperimentalMaterial3Api::class, MapboxExperimental::class)

package uz.futuresoft.mytaxi_task.presentation.mainActivity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.mytaxi_task.R
import uz.futuresoft.mytaxi_task.presentation.components.IconButton
import uz.futuresoft.mytaxi_task.presentation.components.StateSwitch
import uz.futuresoft.mytaxi_task.presentation.components.TextButton
import uz.futuresoft.mytaxi_task.presentation.components.VerticalSpace
import uz.futuresoft.mytaxi_task.presentation.components.bottomSheet.BottomSheet
import uz.futuresoft.mytaxi_task.presentation.mainActivity.event.GetLocationEvent
import uz.futuresoft.mytaxi_task.presentation.mainActivity.event.MainActivityEvent
import uz.futuresoft.mytaxi_task.presentation.service.locationService.LocationService
import uz.futuresoft.mytaxi_task.presentation.service.locationService.hasLocationPermission
import uz.futuresoft.mytaxi_task.presentation.ui.theme.ButtonBackgroundColorLight
import uz.futuresoft.mytaxi_task.presentation.ui.theme.ButtonBorderColorLight
import uz.futuresoft.mytaxi_task.presentation.ui.theme.Green
import uz.futuresoft.mytaxi_task.presentation.ui.theme.IconTintColorLight1
import uz.futuresoft.mytaxi_task.presentation.ui.theme.IconTintColorLight4
import uz.futuresoft.mytaxi_task.presentation.ui.theme.MyTaxiTaskTheme
import uz.futuresoft.mytaxi_task.presentation.ui.theme.OnButtonBackgroundColorLight

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                0
            )
        } else {
            val intent = Intent(applicationContext, LocationService::class.java)
            startService(intent)
        }

        setContent {
            MyTaxiTaskTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    val viewModel: MainActivityViewModel = hiltViewModel()
    val state = viewModel.state.value
    var isSheetExpanded by remember { mutableStateOf(false) }
    val location by remember {
        mutableStateOf(
            Point.fromLngLat(
                state.location?.latitude ?: 0.0,
                state.location?.longitude ?: 0.0
            )
        )
    }
    var zoom by remember { mutableDoubleStateOf(16.0) }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(location)
            zoom(zoom)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.send(GetLocationEvent())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = { MapStyle(style = Style.STANDARD) },
            compass = {},
            scaleBar = {},
            logo = {}
        ) {

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
                    cardColor = ButtonBackgroundColorLight,
                    icon = painterResource(id = R.drawable.ic_hamburger_menu),
                    iconTint = IconTintColorLight1,
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
                            cardColor = OnButtonBackgroundColorLight.copy(alpha = 0.9F),
                            borderWidth = 4.dp,
                            borderColor = ButtonBorderColorLight.copy(alpha = 0.9F),
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
                            cardColor = ButtonBackgroundColorLight.copy(0.9F),
                            icon = painterResource(id = R.drawable.ic_plus),
                            onClick = {
                                mapViewportState.flyTo(
                                    cameraOptions = cameraOptions {
                                        center(location)
                                        zoom(++zoom)
                                    },
                                    animationOptions = MapAnimationOptions.mapAnimationOptions {
                                        duration(500)
                                    },
                                )
                            }
                        )
                        VerticalSpace(height = 16.dp)
                        IconButton(
                            cardColor = ButtonBackgroundColorLight.copy(0.9F),
                            icon = painterResource(id = R.drawable.ic_minus),
                            onClick = {
                                mapViewportState.flyTo(
                                    cameraOptions = cameraOptions {
                                        center(location)
                                        zoom(--zoom)
                                    },
                                    animationOptions = MapAnimationOptions.mapAnimationOptions {
                                        duration(500)
                                    }
                                )
                            }
                        )
                        VerticalSpace(height = 16.dp)
                        IconButton(
                            cardColor = ButtonBackgroundColorLight.copy(0.9F),
                            icon = painterResource(id = R.drawable.ic_navigation),
                            iconTint = IconTintColorLight4,
                            onClick = {
                                zoom = 16.0
                                mapViewportState.flyTo(
                                    cameraOptions = cameraOptions {
                                        center(location)
                                        zoom(zoom)
//                                        zoom(if (zoom < 20.0) zoom++ else zoom)
                                    },
                                    animationOptions = MapAnimationOptions.mapAnimationOptions {
                                        duration(500)
                                    },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActivityPreview() {
    MyTaxiTaskTheme {
        Content()
    }
}