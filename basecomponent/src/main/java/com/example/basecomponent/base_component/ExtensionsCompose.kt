package com.example.basecomponent.base_component

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.basecomponent.base_compose_component.LocationPermissionBottomSheet
import com.example.basecomponent.baseentities.PermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
fun HandleLocationPermission(activity : Activity, onShow: (Boolean)->Unit,onResult : (Boolean)->Unit,){
    var isPermissionGranted by remember { mutableStateOf(activity.checkGpsPermission()) }
    var isGpsLocationOn by remember { mutableStateOf(activity.checkGpsOn()) }
    var isReasoningShow by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(RequestMultiplePermissions()) {
        isPermissionGranted = when{
            it[Manifest.permission.ACCESS_FINE_LOCATION] == true && it[Manifest.permission.ACCESS_FINE_LOCATION] == true ->
                PermissionState.GRANTED
            else -> PermissionState.DENIED
        } }

    val coroutine = rememberCoroutineScope()

    val gpsLauncher =
        rememberLauncherForActivityResult(contract = StartIntentSenderForResult()){
        isGpsLocationOn = it.resultCode == RESULT_OK
    }

    val settingLauncher = rememberLauncherForActivityResult(contract = StartActivityForResult()){
        isReasoningShow = false
        onShow(isReasoningShow)
        coroutine.launch {
            delay(150)
            isPermissionGranted = activity.checkGpsPermission()
            isGpsLocationOn = activity.checkGpsOn()
        }
    }

    LocationPermissionBottomSheet(
        onAccept = {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts(
                    "package", activity.applicationContext.packageName, null,)
                settingLauncher.launch(this) }
        },
        isVisible = isReasoningShow,
        onDenied = {
            activity.finish(); onResult(false); isReasoningShow = false
        }
    )

    LaunchedEffect(isPermissionGranted, isGpsLocationOn, isReasoningShow) {
        launch {
            delay(150)
            onShow(isReasoningShow)
        }
        when{
            isPermissionGranted == PermissionState.NOT_ASKED-> {
                launcher.launch(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION))
                onResult(false)
            }
            isPermissionGranted == PermissionState.DENIED -> {
                launch {
                    delay(300)
                    isReasoningShow = true
                }
            }
            !isGpsLocationOn -> {
                activity.changeLocationSettings(activityResultLauncher = gpsLauncher)
            }
            isPermissionGranted == PermissionState.GRANTED -> onResult(true)
        }
    }
}

@Composable
fun Activity.ComposableLifecycle(
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: @Composable (Lifecycle.Event) -> Unit
) {
    var lifecycleEvent by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    onEvent(lifecycleEvent)

    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            lifecycleEvent = event
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 1f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}