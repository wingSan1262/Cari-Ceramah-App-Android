package com.example.data.repo.remote

import com.example.data.entities.FcmRegisterEntity
import com.example.data.entities.LectureEntity
import com.example.data.entities.LectureResponseEntity
import com.example.data.entities.ResponseEntity
import com.example.data.entities.request.FcmRequest
import com.example.data.entities.request.LectureRequest

interface KhutbahApiInterface {
    suspend fun registerFcm(
        request: FcmRequest
    ) : ResponseEntity<FcmRegisterEntity>
    suspend fun unregisterFcm(
        request: FcmRequest
    ) : ResponseEntity<Boolean>
    suspend fun fetchLectures(
        request : LectureRequest
    ) : ResponseEntity<LectureResponseEntity>
    suspend fun searchLectures(
        request : LectureRequest
    ) : ResponseEntity<LectureResponseEntity>
}