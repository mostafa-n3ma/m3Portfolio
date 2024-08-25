package org.example.m3portfolio.util

import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.LANGUAGES_SPLITTER_CODE
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Visitor
import org.example.m3portfolio.models.Website

data class BigObjectUiState(
    var infoObject: Info = Info(),
    var experiencesList: List<Experience> = listOf(),
    var projectsList: List<Project> = listOf(),
    var certificatesList: List<Certificate> = listOf(),
    var websitesList: List<Website> = listOf(),
    var messagePopup: String = "",
    var visitCount :Int = 0,
    var visitor: Visitor = Visitor()
)


fun BigObjectUiState.splitLanguages(displayLanguage: Constants.Languages): BigObjectUiState {
    val langCode = when (displayLanguage) {
        Constants.Languages.EN -> 0
        Constants.Languages.AR -> 1
    }
    return try {
        this.copy(
            infoObject = if (this.infoObject != Info()) {
                this.infoObject.copy(
                    name = this.infoObject.name.split(LANGUAGES_SPLITTER_CODE)
                        .getOrElse(langCode) { this.infoObject.name.split(LANGUAGES_SPLITTER_CODE)[0] },
                    imageUrl = this.infoObject.imageUrl, // Assuming URL doesn't change based on language
                    role = this.infoObject.role.split(LANGUAGES_SPLITTER_CODE)
                        .getOrElse(langCode) { this.infoObject.role.split(LANGUAGES_SPLITTER_CODE)[0] },
                    address = this.infoObject.address.split(LANGUAGES_SPLITTER_CODE)
                        .getOrElse(langCode) { this.infoObject.address.split(LANGUAGES_SPLITTER_CODE)[0] },
                    phone = this.infoObject.phone, // Assuming phone number doesn't change based on language
                    email = this.infoObject.email, // Assuming email doesn't change based on language
                    linkedIn = this.infoObject.linkedIn, // Assuming LinkedIn URL doesn't change based on language
                    github = this.infoObject.github, // Assuming GitHub URL doesn't change based on language
                    bio = this.infoObject.bio.split(LANGUAGES_SPLITTER_CODE)
                        .getOrElse(langCode) { this.infoObject.bio.split(LANGUAGES_SPLITTER_CODE)[0] },
                    about = this.infoObject.about.split(LANGUAGES_SPLITTER_CODE)
                        .getOrElse(langCode) { this.infoObject.about.split(LANGUAGES_SPLITTER_CODE)[0] },
                    education = this.infoObject.education.split(LANGUAGES_SPLITTER_CODE)
                        .getOrElse(langCode) {
                            this.infoObject.education.split(LANGUAGES_SPLITTER_CODE)[0]
                        },
                    skills = this.infoObject.skills.split(",").joinToString(",") { skill ->
                        skill.split(LANGUAGES_SPLITTER_CODE).getOrElse(langCode) { skill }
                    },
                    programLanguages = this.infoObject.programLanguages.split(",")
                        .joinToString(",") { language ->
                            language.split(LANGUAGES_SPLITTER_CODE).getOrElse(langCode) { language }
                        },
                    tools = this.infoObject.tools.split(",").joinToString(",") { tool ->
                        tool.split(LANGUAGES_SPLITTER_CODE).getOrElse(langCode) { tool }
                    },
                    frameWorks = this.infoObject.frameWorks.split(",")
                        .joinToString(",") { framework ->
                            framework.split(LANGUAGES_SPLITTER_CODE)
                                .getOrElse(langCode) { framework }
                        },
                    resumeLink = this.infoObject.resumeLink, // Assuming resume link doesn't change based on language
                    extra = this.infoObject.extra.split(LANGUAGES_SPLITTER_CODE)
                        .getOrElse(langCode) { this.infoObject.extra.split(LANGUAGES_SPLITTER_CODE)[0] },
                )
            } else {
                this.infoObject
            },
            experiencesList = if (experiencesList.isNotEmpty()) {
                experiencesList.map { expItem ->
                    expItem.copy(
                        description = expItem.description.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { expItem.description.split(LANGUAGES_SPLITTER_CODE)[0] },
                        duration = expItem.duration.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { expItem.duration.split(LANGUAGES_SPLITTER_CODE)[0] },
                        image = expItem.image, // Assuming image URL doesn't change based on language
                        location = expItem.location.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { expItem.location.split(LANGUAGES_SPLITTER_CODE)[0] },
                        projects = expItem.projects.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { expItem.projects.split(LANGUAGES_SPLITTER_CODE)[0] },
                        role = expItem.role.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { expItem.role.split(LANGUAGES_SPLITTER_CODE)[0] }
                    )
                }
            } else {
                experiencesList
            },
            projectsList = if (projectsList.isNotEmpty()) {
                projectsList.reversed().map { projectItem ->
                    projectItem.copy(
                        title = projectItem.title.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { projectItem.title.split(LANGUAGES_SPLITTER_CODE)[0] },
                        subTitle = projectItem.subTitle.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) {
                                projectItem.subTitle.split(LANGUAGES_SPLITTER_CODE)[0]
                            },
                        description = projectItem.description.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) {
                                projectItem.description.split(LANGUAGES_SPLITTER_CODE)[0]
                            },
                        techStack = projectItem.techStack.split(",")
                            .joinToString(",") { teck ->
                                teck.split(LANGUAGES_SPLITTER_CODE)
                                    .getOrElse(langCode) { teck }
                            },
                        repoLink = projectItem.repoLink, // Assuming repo link doesn't change based on language
                        videoLink = projectItem.videoLink, // Assuming video link doesn't change based on language
                        mainImageLink = projectItem.mainImageLink, // Assuming image link doesn't change based on language
                        imagesList = projectItem.imagesList,
                        date = projectItem.date
                    )
                }
            } else {
                projectsList
            },
            certificatesList = if (certificatesList.isNotEmpty()) {
                certificatesList.map { certificateItem ->
                    certificateItem.copy(
                        title = certificateItem.title.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { certificateItem.title.split(LANGUAGES_SPLITTER_CODE)[0] },
                        from = certificateItem.from.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { certificateItem.from.split(LANGUAGES_SPLITTER_CODE)[0] },
                        link = certificateItem.link, // Assuming link doesn't change based on language
                        date = certificateItem.date,
                        thumbnailLink = certificateItem.thumbnailLink // Assuming thumbnail link doesn't change based on language
                    )
                }
            } else { certificatesList },
            websitesList = if (websitesList.isNotEmpty()) {
                websitesList.map { websiteItem ->
                    websiteItem.copy(
                        title = websiteItem.title.split(LANGUAGES_SPLITTER_CODE)
                            .getOrElse(langCode) { websiteItem.title.split(LANGUAGES_SPLITTER_CODE)[0] },
                        link = websiteItem.link, // Assuming link doesn't change based on language
                        icon = websiteItem.icon // Assuming icon doesn't change based on language
                    )
                }
            } else { websitesList },
        )
    } catch (e: Exception) {
        println("from bigObject.splitLanguages${e}")
        this
    }
}

