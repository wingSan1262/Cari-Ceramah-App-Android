package com.example.common_usecase

import com.example.basecomponent.base_component.BaseUseCase
import com.example.basecomponent.base_component.utils.LocationService
import com.example.data.entities.ResponseEntity
import com.example.data.repo.local.PrefInterface

class SetFcmTokenUseCase(
    val prefInterface: PrefInterface
) : BaseUseCase<String, Any?>() {

    override fun setup(parameter: String) {
        super.setup(parameter)
        execute {
            prefInterface.setNotificationFcmStatus(parameter)
            return@execute ResponseEntity(200, "OK", null)
        }
    }
}