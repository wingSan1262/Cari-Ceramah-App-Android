package com.example.data.repo.local

import android.app.Activity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.basecomponent.base_component.PreferenceKey
import com.example.basecomponent.base_component.PreferenceKey.Companion.DEVICE_LOCATIOn
import com.example.basecomponent.base_component.PreferenceKey.Companion.IS_ONBOARD
import com.example.basecomponent.base_component.PreferenceKey.Companion.USER_FCM_TOKEN
import com.example.basecomponent.base_component.utils.LocationService
import com.example.basecomponent.base_component.utils.LocationService.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PrefInterface {
    suspend fun saveLocationCoordinate(coordinateData: coordinateData)
    suspend fun getSavedLocationCoordinate(): coordinateData
    suspend fun getNotificationFcmStatus(): String
    suspend fun setNotificationFcmStatus(tokenString : String)
    suspend fun setOnboardStatus()
    suspend fun getOnboardStatus(): Boolean
}

class PrefIml (
    val pref : DataStore<Preferences>
) : PrefInterface {

    override suspend fun saveLocationCoordinate(coordinateData: coordinateData) {
        pref.edit {
            it[DEVICE_LOCATIOn] = Gson().toJson(coordinateData)
        }
    }

    override suspend fun getSavedLocationCoordinate(): coordinateData {
        var coordinate : coordinateData? = null
        try{
            pref.data.map {
                return@map Gson().fromJson(it[DEVICE_LOCATIOn], coordinateData::class.java)
            }.cancellable().collect{
                coordinate = it
                throw java.lang.IllegalArgumentException("fail to get Device Location")
            }
        } catch(e:Throwable) {
            if (coordinate == null) throw java.lang.IllegalArgumentException("fail to get Device Location")
        }
        return coordinate!!
    }

    override suspend fun getNotificationFcmStatus(): String {
        var token = ""
        try{
            pref.data.map {
                return@map it[USER_FCM_TOKEN]
            }.cancellable().collect{
                token = it ?: ""
                throw java.lang.IllegalArgumentException("fail to get Device Location")
            }
        } catch (e : Throwable){

        }
        return token
    }

    override suspend fun setNotificationFcmStatus(tokenString: String) {
        pref.edit {
            it[USER_FCM_TOKEN] = tokenString
        }
    }

    override suspend fun setOnboardStatus() {
        pref.edit {
            it[IS_ONBOARD] = "onboarded"
        }
    }

    override suspend fun getOnboardStatus(): Boolean {
        var token = ""
        try{
            pref.data.map {
                return@map it[IS_ONBOARD]
            }.cancellable().collect{
                token = it ?: ""
                throw java.lang.IllegalArgumentException("fail to get Device Location")
            }
        } catch (e : Throwable){

        }
        return token.isNotEmpty()
    }

}