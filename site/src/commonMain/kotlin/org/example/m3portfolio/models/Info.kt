package org.example.m3portfolio.models
import kotlinx.serialization.Serializable




@Serializable
data class Info(
    var _id: String = "",
    val name: String="",
    val imageUrl: String="",
    val role: String="",
    val address: String="",
    val phone: String="",
    val email: String="",
    val linkedIn: String="",
    val github: String="",
    val bio: String="",
    val education: String="",
    val experience: List<String> = listOf(""),
    val skills: String="",
    val resumeLink: String="",
    val extra: String=""
)


