package org.example.m3portfolio.models

import kotlinx.serialization.Serializable


// Website entity
@Serializable
data class Website(
    var _id:String="",
    var title: String="",
    var link: String="",
    var icon: String = ""
)