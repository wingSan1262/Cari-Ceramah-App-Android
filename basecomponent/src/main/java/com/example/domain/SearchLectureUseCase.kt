package com.example.domain

import com.example.basecomponent.base_component.BaseUseCase
import com.example.data.entities.ResponseEntity
import com.example.data.entities.request.LectureRequest
import com.example.data.entities.toModel
import com.example.data.repo.remote.KhutbahApiInterface
import com.example.domain.model.LectureContentModel

class SearchLectureUseCase(
    val khutbahApiInterface: KhutbahApiInterface
) : BaseUseCase<LectureRequest, LectureContentModel>() {

    override fun setup(parameter: LectureRequest) {
        super.setup(parameter)
        execute {
            khutbahApiInterface.searchLectures(parameter).run {
                return@execute ResponseEntity( code, status,  content.toModel())
            }
        }
    }

}