package com.example.data.repo.remote

import com.example.data.entities.FcmRegisterEntity
import com.example.data.entities.LectureEntity
import com.example.data.entities.LectureResponseEntity
import com.example.data.entities.ResponseEntity
import com.example.data.entities.request.FcmRequest
import com.example.data.entities.request.LectureRequest

class KhutbahApiIml(
    val khutbahInterface: KhutbahInterface
) : KhutbahApiInterface {
    override suspend fun registerFcm(request: FcmRequest): ResponseEntity<FcmRegisterEntity> {
        return khutbahInterface.registerFcm(
            request
        )
    }
    override suspend fun unregisterFcm(request: FcmRequest): ResponseEntity<Boolean> {
        return khutbahInterface.unregisterFcm(
            request
        )
    }
    override suspend fun fetchLectures(request: LectureRequest): ResponseEntity<LectureResponseEntity> {
        request.run {
            return khutbahInterface.fetchLectures(
                request
            )
        }
    }
    override suspend fun searchLectures(request: LectureRequest): ResponseEntity<LectureResponseEntity> {
        request.run {
            return khutbahInterface.searchLectures(
                request
            )
        }
    }


}