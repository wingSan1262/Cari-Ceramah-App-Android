package com.example.domain

import com.example.basecomponent.base_component.BaseUseCase
import com.example.data.entities.request.FcmRequest
import com.example.data.repo.local.PrefInterface
import com.example.data.repo.remote.KhutbahApiInterface

class UnregisterFcmUseCase(
    val khutbahApiInterface: KhutbahApiInterface,
    val prefInterface: PrefInterface
) : BaseUseCase<FcmRequest, Boolean>() {
    override fun setup(parameter: FcmRequest) {
        super.setup(parameter)
        execute {
            prefInterface.setNotificationFcmStatus("")
            return@execute khutbahApiInterface.unregisterFcm(parameter)
        }
    }
}