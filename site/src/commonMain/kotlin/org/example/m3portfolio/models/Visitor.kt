package org.example.m3portfolio.models

import kotlinx.serialization.Serializable


@Serializable
data class Visitor(
    var _id:String="",
    var date:String= ""
)