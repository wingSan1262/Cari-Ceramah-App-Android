package com.example.data.entities

data class ResponseEntity<Content> (
    val code : Int,
    val status : String,
    val content : Content
)