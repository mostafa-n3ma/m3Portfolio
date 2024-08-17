package org.example.m3portfolio.models

import kotlinx.serialization.Serializable

@Serializable
data class Gallery (
    var _id:String = "",
    var category:String = "",
    var base_64:String = ""
)