package com.example.common_usecase

import com.example.basecomponent.base_component.BaseUseCase
import com.example.basecomponent.base_component.utils.LocationService
import com.example.data.entities.ResponseEntity
import com.example.data.repo.local.PrefInterface

class GetFcmTokenUseCase(
    val prefInterface: PrefInterface
) : BaseUseCase<Any?, String>() {

    override fun setup(parameter: Any?) {
        super.setup(parameter)
        execute {
            prefInterface.getNotificationFcmStatus().let{
                if(it.isEmpty())
                    throw Throwable("user not registered yet")
                else return@execute ResponseEntity(200, "OK", it)
            }
        }
    }
}