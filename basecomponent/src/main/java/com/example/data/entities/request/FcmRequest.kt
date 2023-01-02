package com.example.data.entities.request

data class FcmRequest (
    var fcm_token  : String,
    var latitude  : Double = 0.0,
    var longitude : Double = 0.0
)