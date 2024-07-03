package org.example.m3portfolio.models

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val _id:String,
    val title: String,
    val description: String,
    val techStack: String,
    val repoLink: String,
    val videoLink: String,
    val mainImageLink: String,
    val imagesList: List<String> = listOf(""),
    val date: String
)
