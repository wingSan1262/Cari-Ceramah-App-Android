package com.example.domain

import com.example.basecomponent.base_component.BaseUseCase
import com.example.common_usecase.SetFcmTokenUseCase
import com.example.data.entities.FcmRegisterEntity
import com.example.data.entities.request.FcmRequest
import com.example.data.repo.local.PrefInterface
import com.example.data.repo.remote.KhutbahApiInterface

class RegisterFcmUseCase(
    val khutbahApiInterface: KhutbahApiInterface,
    val prefInterface: PrefInterface
) : BaseUseCase<FcmRequest, FcmRegisterEntity>() {

    override fun setup(parameter: FcmRequest) {
        super.setup(parameter)
        execute {
            val token = khutbahApiInterface.registerFcm(parameter)
            prefInterface.setNotificationFcmStatus(token.content.fcmToken)
            return@execute token
        }
    }

}