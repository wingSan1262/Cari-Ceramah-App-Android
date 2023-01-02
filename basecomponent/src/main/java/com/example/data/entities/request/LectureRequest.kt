package com.example.data.entities.request

data class LectureRequest(
    var query         : String = "",
    var lat           : Double,
    var lng           : Double,
    var searchingDate : String,
    var pageItemCount : Int,
    var page          : Int
)
