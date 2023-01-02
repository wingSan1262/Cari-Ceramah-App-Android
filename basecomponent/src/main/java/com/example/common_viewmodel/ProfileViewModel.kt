package com.example.common_viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basecomponent.base_component.getContent
import com.example.basecomponent.base_component.utils.LocationService
import com.example.common_usecase.GetFcmTokenUseCase
import com.example.data.entities.request.FcmRequest
import com.example.data.repo.local.PrefInterface
import com.example.domain.RegisterFcmUseCase
import com.example.domain.UnregisterFcmUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    val registerFcmUseCase: RegisterFcmUseCase,
    val unregisterFcmUseCase: UnregisterFcmUseCase,
    val getFcmTokenUseCase: GetFcmTokenUseCase,
    val prefInterface: PrefInterface
) : ViewModel() {

    val firebase: FirebaseMessaging = FirebaseMessaging.getInstance()

    val registerFcmStatus = registerFcmUseCase.currentData
    fun registerFcm(
        coordinateData: LocationService.coordinateData
    ){
        firebase.token.addOnCompleteListener { task ->
            if (!task.isSuccessful())
                return@addOnCompleteListener
            val token: String = task.getResult()
            registerFcmUseCase.setup(
                FcmRequest(
                token, coordinateData.lat, coordinateData.lon
            )
            )
        }
    }

    val getFcmData = getFcmTokenUseCase.currentData
    fun getFcmToken(){getFcmTokenUseCase.setup(null)}

    val unregisterFcmStatus = unregisterFcmUseCase.currentData
    fun unregisterFcm(){
        val token = getFcmData.value?.nonFilteredContent()?.getContent()
        unregisterFcmUseCase.setup(
            FcmRequest(token ?: "")
        )
    }

    val _isOnboard = MutableLiveData<Boolean?>(null)
    val isOnBoard : LiveData<Boolean?> = _isOnboard
    fun getOnBoardStatus (){
        viewModelScope.launch(Dispatchers.IO) {
            prefInterface.getOnboardStatus().let { stat ->
                (Dispatchers.Main){ _isOnboard.value = stat } } } }
    fun setOnBoardStatue(){
        viewModelScope.launch(Dispatchers.IO) {
            prefInterface.setOnboardStatus()
        }
    }
}

