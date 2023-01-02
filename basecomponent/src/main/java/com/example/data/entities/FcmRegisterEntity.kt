package com.example.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class FcmRegisterEntity(
        @SerializedName("fcm_token" ) var fcmToken  : String = "",
        @SerializedName("createdAt" ) var createdAt : Long    = 0,
        @SerializedName("latitude"  ) var latitude  : Double = 0.0,
        @SerializedName("longitude" ) var longitude : Double = 0.0
) : Parcelable

