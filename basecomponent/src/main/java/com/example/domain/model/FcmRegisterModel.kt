package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FcmRegisterModel(
        var fcmToken  : String = "",
        var createdAt : Int    = 0,
        var latitude  : Double = 0.0,
        var longitude : Double = 0.0
) : Parcelable

