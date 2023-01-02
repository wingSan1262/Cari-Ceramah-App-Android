package com.example.cariceramah

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.example.basecomponent.R.*
import com.example.basecomponent.base_component.ActivityName
import com.example.basecomponent.base_component.appUserPreference
import com.example.basecomponent.base_component.loadWithGlide
import com.example.basecomponent.base_component.utils.LocationService
import com.example.basecomponent.theme.BLUE_001D6E
import com.example.data.repo.local.PrefIml
import com.example.data.repo.local.PrefInterface
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.*
import kotlinx.parcelize.Parcelize
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

class FcmService : FirebaseMessagingService() {

    var prefInterface: PrefInterface? = null

    companion object{
        val LECTURE_NOTIF = "lecture_notif"
        val LECTURE_GROUP_NOTIF = "notif_lecture_group"
    }

    override fun onMessageReceived(message: RemoteMessage) {
        prefInterface = PrefIml(this.application.appUserPreference)
        if(prefInterface == null) return
        CoroutineScope(Dispatchers.IO).launch{
            val notifData = NotificationData(
                message.data,
                prefInterface!!.getSavedLocationCoordinate())
            loadWithGlide(notifData.imageUrl){
                showNotification(it, notifData)
            }
        }
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    fun showNotification(bitmap: Bitmap, notificationData: NotificationData){
        val notifManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= VERSION_CODES.O) setupChannels(notifManager)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        Intent(this, Class.forName(ActivityName.HOME_ACTIVITY)).apply {
            val random = Math.random()
            action = random.toString()
            putExtras(bundleOf(LECTURE_NOTIF to notificationData))
            val flags = when {
                Build.VERSION.SDK_INT >= VERSION_CODES.M -> FLAG_UPDATE_CURRENT or FLAG_MUTABLE
                else -> FLAG_UPDATE_CURRENT
            }
            val pendingInt = PendingIntent.getActivity(this@FcmService,
                random.toInt(), this, flags)
            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(this@FcmService, "ADMIN_CHANNEL_ID")

            notificationBuilder.setSmallIcon(
                drawable.ic_launcher_foreground)
            notificationBuilder.color = ContextCompat.getColor(this@FcmService, R.color.blue_001D6E);
            notificationBuilder.setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    drawable.notification_logo))

            notificationData.run {
                val distance = Math.round(distance * 100.0) / 100.0
                val estTime = Math.round(distance / 20 * 60 * 100) / 100
                notificationBuilder.setContentTitle(
                    "$title | $lecturer ")
                    .setGroup(LECTURE_GROUP_NOTIF)
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("($dateDisplay | $distance Km $estTime ${this@FcmService.getString(
                            string.label_min)})\n\n" +
                                "$description"))
                    .setSound(soundUri)
                    .setLargeIcon(bitmap)
                    .setContentIntent(pendingInt)
                notifManager.notify(
                    LECTURE_GROUP_NOTIF, random.toInt(), notificationBuilder.build()
                )
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager : NotificationManager) {
        val adminChannelName: CharSequence = "Channel"
        val adminChannelDescription = "Channel Desc"
        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(
            "ADMIN_CHANNEL_ID",
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }
}

@Parcelize
data class NotificationData(
    val imageUrl : String,
    val lectureId : String,
    val description : String,
    val distance : Double,
    val dateDisplay : String,
    val title : String,
    val lecturer : String
) : Parcelable {
    constructor(map : Map<String,String>, userCoor: LocationService.coordinateData) : this(
        imageUrl = map["imageUrl"].toString().run {
            return@run if(this.contains("https")) this
                else this.replace("http", "https")
        },
        lectureId = map["id"].toString(),
        description = map["description"].toString(),
        distance = getDistance(
            userCoor,
            map["lat"]?.toDouble() ?: 0.0,
            map["lng"]?.toDouble() ?: 0.0
        ),
        dateDisplay = map["date"].toString(),
        title = map["title"].toString(),
        lecturer = map["lecturer"].toString(),
    )
}

private fun getDistance(
    userCoor : LocationService.coordinateData
    , lectLat : Double, lectLng : Double) :  Double {
    return sqrt(
        (112.2 * (lectLat-userCoor.lat)).pow(2) +
            (112.2 * (userCoor.lon-lectLng) * cos(lectLat/57.3)).pow(2))
}
