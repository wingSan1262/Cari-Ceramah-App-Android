package com.example.data.repo.remote

import com.example.data.entities.FcmRegisterEntity
import com.example.data.entities.LectureEntity
import com.example.data.entities.LectureResponseEntity
import com.example.data.entities.ResponseEntity
import com.example.data.entities.request.FcmRequest
import com.example.data.entities.request.LectureRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KhutbahInterface {
    @POST("api/notification/register")
    suspend fun registerFcm(
        @Body request: FcmRequest
    ) : ResponseEntity<FcmRegisterEntity>

    @POST("api/notification/unregister")
    suspend fun unregisterFcm(
        @Body request: FcmRequest
    ) : ResponseEntity<Boolean>

    @POST("api/lecture/fetch")
    suspend fun fetchLectures(
        @Body request: LectureRequest
    ) : ResponseEntity<LectureResponseEntity>

    @POST("api/lecture/search")
    suspend fun searchLectures(
        @Body request: LectureRequest
    ) : ResponseEntity<LectureResponseEntity>
}