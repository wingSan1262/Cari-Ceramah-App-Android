package com.example.basecomponent.base_component

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.basecomponent.R
import com.example.basecomponent.baseentities.PermissionState
import com.example.basecomponent.baseentities.ResourceState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.tasks.Task
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun Long.toLocalTime(): Date {
    return Date(this)
}

fun Date.getDateDisplay(): String {
    val pattern = "dd MMMM yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(this)
}

fun Date.getDateForRequest(): String {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss"
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(this)
}

fun Date.getHour(): String {
    val pattern = "HH:mm"
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(this)
}

fun Date.incrementDate(dayAfterCount : Int): Date {
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.DATE, dayAfterCount)
    return c.time
}

fun Date.incrementHour(dayAfterCount : Int): Date {
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.HOUR, dayAfterCount)
    return c.time
}

fun Int.getRupiahValueFormat(): String? {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    val decimalFormat = (formatter as DecimalFormat).decimalFormatSymbols
    decimalFormat.currencySymbol = ""
    (formatter).decimalFormatSymbols = decimalFormat
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}

/**
 * Extension method to help on observing the event object
 * to reduce unwanted boiler on view file end
 */

fun Activity.showToast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

val Context.appUserPreference: DataStore<Preferences> by preferencesDataStore(
    name = "app"
)

fun <T> ResourceState<T>.getContent(): T? {
    return if(this is ResourceState.Success) this.body
        else if (this is ResourceState.Failure) this.body
            else null
}

fun Context.loadWithGlide(
    imageUrl : String, onLoaded : (Bitmap)->Unit){
    Glide.with(this)
        .asBitmap()
        .load(imageUrl)
        .listener(object : RequestListener<Bitmap>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoaded(BitmapFactory.decodeResource(resources, R.drawable.onboard_logo))
                return true
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                resource?.let { onLoaded(it) }
                return true
            }

        })
        .into( object : CustomTarget<Bitmap>(){
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                onLoaded(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
                val a = "sdaw"
            } })
}

@Composable
fun InvertComposeLayoutRtl(
     content : @Composable  ()->Unit
){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ){
        content()
    }
}

@Composable
fun InvertComposeLayoutLtr(
    content : @Composable  ()->Unit
){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr ){
        content()
    }
}

fun Activity.openMaps(lat:Double, lon:Double, mTitle : String){
    val uri = "http://maps.google.com/maps?q=loc:$lat,$lon($mTitle)"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}

fun Activity.doPhoneCall(phoneAdd : String){
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneAdd"))
    startActivity(intent)
}

fun Activity.checkGpsPermission(): PermissionState {
    return when(PackageManager.PERMISSION_GRANTED){
        checkSelfPermission(this, ACCESS_COARSE_LOCATION),
        checkSelfPermission(this, ACCESS_FINE_LOCATION)->{
            PermissionState.GRANTED
        }
        else -> PermissionState.NOT_ASKED
    }
}

fun Activity.checkGps(){

}

fun Activity.checkGpsOn(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.isLocationEnabled
    } else {
        val mode = Settings.Secure.getInt(
            contentResolver, Settings.Secure.LOCATION_MODE,
            Settings.Secure.LOCATION_MODE_OFF)
        mode != Settings.Secure.LOCATION_MODE_OFF
    }
}

private fun Activity.getLocationRequest(): LocationRequest {
    return LocationRequest
        .Builder(PRIORITY_BALANCED_POWER_ACCURACY, 300L)
        .build()
}

fun Activity.changeLocationSettings(
    locationRequest: LocationRequest = getLocationRequest(),
    activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>
) {
    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    val client = LocationServices.getSettingsClient(this)
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
    task.addOnSuccessListener{ }.addOnFailureListener { e ->
        if (e is ResolvableApiException) {
            val intentSenderRequest =
                IntentSenderRequest.Builder(e.resolution).build()
            activityResultLauncher.launch(intentSenderRequest)
        } }
}

fun Double.getOneDecimalFormat(): Double {
    return Math.round(this * 10.0) / 10.0
}

