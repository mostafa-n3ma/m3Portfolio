package org.example.m3portfolio.models

import kotlinx.serialization.Serializable


// Website entity
@Serializable
data class Website(
    val _id:Int?=null,
    val title: String,
    val link: String
)