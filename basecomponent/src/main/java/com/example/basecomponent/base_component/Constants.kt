package com.example.basecomponent.base_component

import androidx.datastore.preferences.core.stringPreferencesKey

class Constants {
}

class ActivityName{
    companion object{
        val HOME_ACTIVITY = "com.example.homescreen.HomeActivity"
    }
}

class PreferenceKey{
    companion object{
        val DEVICE_LOCATIOn = stringPreferencesKey("device_lat_lng")
        val USER_FCM_TOKEN = stringPreferencesKey("fcm_token")
        val IS_ONBOARD = stringPreferencesKey("onboard_status")
        val USER_INFO = stringPreferencesKey("user_info")
    }
}

class ScreenRoute{
    companion object{
        val SPLASH_SCREEN = "splash_screen"
        val ONBOARDING_SCREEN = "onboarding_screen"
        val LOGIN_LOGOUT_SCREEN = "login_logout_screen"
    }
}