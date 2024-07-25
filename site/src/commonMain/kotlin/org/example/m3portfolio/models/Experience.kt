package org.example.m3portfolio.models

import kotlinx.serialization.Serializable


@Serializable
data class Experience(
    var _id: String = "",
    val description: String = "",
    val duration: String = "",
    val image: String = "",
    val location: String = "",
    val projects: String = "",
    val role: String = "",
)
