package com.example.common_usecase

import com.example.basecomponent.base_component.BaseUseCase
import com.example.basecomponent.base_component.utils.LocationService
import com.example.data.entities.ResponseEntity
import com.example.data.repo.local.PrefInterface

class LocationUseCase(
    val locationService: LocationService,
    val prefInterface: PrefInterface
) : BaseUseCase<Any?, LocationService.coordinateData>() {

    override fun setup(parameter: Any?) {
        super.setup(parameter)
        execute {
            locationService.getLatAndLon()?.let{
                prefInterface.saveLocationCoordinate(it)
                return@execute ResponseEntity(200, "OK", it)
            } ?: throw java.lang.IllegalArgumentException("failed to get GPS location")
        }
    }
}