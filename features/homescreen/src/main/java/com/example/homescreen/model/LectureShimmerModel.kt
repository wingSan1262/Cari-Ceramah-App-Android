package com.example.homescreen.model

import com.example.basecomponent.base_component.BaseListModel
import java.util.UUID

data class LectureShimmerModel(
    override val id: String = UUID.randomUUID().toString(),
    val type : String = "shimmer_lecture",
) : BaseListModel