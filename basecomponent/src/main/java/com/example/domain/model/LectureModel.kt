package com.example.domain.model

import android.os.Parcelable
import com.example.basecomponent.base_component.BaseListModel
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList


@Parcelize
data class LectureContentModel(
        var page        : Int = 0,
        var itemPerPage : Int             = 0,
        var items       : ArrayList<LectureModel> = arrayListOf()
) : Parcelable

@Parcelize
data class LectureModel(
        override var id          : String           = "",
        var organizerId : String           = "",
        var name        : String           = "",
        var lecturer    : String           = "",
        var description : String           = "",
        var theme       : String           = "",
        var book        : String           = "",
        var placeName   : String           = "",
        var latitude    : Double           = 0.0,
        var longitude   : Double           = 0.0,
        var startDate   : Date = Date(),
        var finishDate  : Date = Date(),
        var phone       : String           = "",
        var price       : Int              = 0,
        var distance    : Double           = 0.0,
        var minuteDiff  : Int              = 0,
        var address     : String           = "",
        var city        : String           = "",
        var listImages  : ArrayList<String> = arrayListOf()
) : Parcelable, BaseListModel


