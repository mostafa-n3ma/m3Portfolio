package org.example.m3portfolio.models

import kotlinx.serialization.Serializable

@Serializable
data class Certificate(
    val _id:Int?=null,
    val title: String,
    val from: String,
    val link: String,
    val date: String
)
