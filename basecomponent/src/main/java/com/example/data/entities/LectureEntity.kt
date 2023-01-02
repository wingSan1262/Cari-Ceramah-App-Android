package com.example.data.entities

import android.os.Parcelable
import com.example.basecomponent.base_component.toLocalTime
import com.example.domain.model.LectureContentModel
import com.example.domain.model.LectureModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class LectureResponseEntity(
        @SerializedName("page"        ) var page        : Int = 0,
        @SerializedName("itemPerPage" ) var itemPerPage : Int             = 0,
        @SerializedName("items"       ) var items       : ArrayList<LectureEntity> = arrayListOf()
) : Parcelable

@Parcelize
data class LectureEntity(
        @SerializedName("id"          ) var id          : String           = "",
        @SerializedName("organizerId" ) var organizerId : String           = "",
        @SerializedName("name"        ) var name        : String           = "",
        @SerializedName("lecturer"    ) var lecturer    : String           = "",
        @SerializedName("description" ) var description : String           = "",
        @SerializedName("theme"       ) var theme       : String           = "",
        @SerializedName("book"        ) var book        : String           = "",
        @SerializedName("placeName"   ) var placeName   : String           = "",
        @SerializedName("latitude"    ) var latitude    : Double           = 0.0,
        @SerializedName("longitude"   ) var longitude   : Double           = 0.0,
        @SerializedName("startDate"   ) var startDate   : Long              = 0,
        @SerializedName("finishDate"  ) var finishDate  : Long              = 0,
        @SerializedName("phone"       ) var phone       : String           = "",
        @SerializedName("price"       ) var price       : Int              = 0,
        @SerializedName("distance"    ) var distance    : Double           = 0.0,
        @SerializedName("minuteDiff"  ) var minuteDiff  : Int              = 0,
        @SerializedName("address"     ) var address     : String           = "",
        @SerializedName("city"        ) var city        : String           = "",
        @SerializedName("listImages"  ) var listImages  : ArrayList<String> = arrayListOf()
) : Parcelable

fun LectureResponseEntity.toModel(): LectureContentModel {
        val a = items.map {
                it.toModel()
        }
        return LectureContentModel(
                page, itemPerPage,
                ArrayList(items.map { it.toModel() })
        )
}

fun LectureEntity.toModel() : LectureModel {
        return LectureModel(
                id, organizerId, name, lecturer,
                description, theme, book, placeName,
                latitude, longitude, startDate.toLocalTime(), finishDate.toLocalTime(),
                phone, price, distance, minuteDiff,
                address, city, listImages
        )
}
