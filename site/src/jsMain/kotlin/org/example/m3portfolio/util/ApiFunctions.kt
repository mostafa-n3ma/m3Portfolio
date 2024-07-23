package org.example.m3portfolio.util

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.m3portfolio.ApiPaths
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.EXPERIENCE_ID_PARAM
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.ApiExperienceResponse
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.User
import org.example.m3portfolio.models.UserWithoutPassword


suspend fun requestInfoData(
    onSuccess: (ApiInfoResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val resultInfo = window.api.tryGet(
            apiPath = ApiPaths.READ_INFO_PATH
        )?.decodeToString()
        onSuccess(Json.decodeFromString(resultInfo.toString()))
    } catch (e: Exception) {
        onError(e)
    }

}

suspend fun requestInfoDataUpdate(info: Info): Boolean {
    return try {
        window.api.tryPost(
            apiPath = ApiPaths.UPDATE_INFO_PATH,
            body = Json.encodeToString(info).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}


suspend fun requestExperienceData(
    onSuccess: (ApiExperienceResponse) -> Unit,
    onError: (Exception) -> Unit
) {

    return try {
        val resultExperience: String? = window.api.tryGet(
            apiPath = ApiPaths.READ_EXPERIENCE_PATH
        )?.decodeToString()
        onSuccess(Json.decodeFromString(resultExperience.toString()))
    } catch (e: Exception) {
        onError(e)
    }
}



suspend fun requestExperienceDataById(id: String):ApiExperienceResponse{
    return try {
        val selectedExperience: String? = window.api.tryGet(
             apiPath = "${ApiPaths.READ_EXPERIENCE_BY_ID_PATH}?${EXPERIENCE_ID_PARAM}=$id"
         )?.decodeToString()

        ApiExperienceResponse.Success(data = Json.decodeFromString<List<Experience>>(selectedExperience.toString()) )

    }catch (e:Exception){
        ApiExperienceResponse.Error(message = e.message.toString())
    }
}


suspend fun requestExperienceDataUpdate(experience: Experience): Boolean {
    return try {
        window.api.tryPost(
            apiPath = ApiPaths.UPDATE_EXPERIENCE_PATH,
            body = Json.encodeToString(experience).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}


suspend fun requestExperienceAdd(experience: Experience): Boolean {
    return try {
        window.api.tryPost(
            apiPath = ApiPaths.ADD_EXPERIENCE_PATH,
            body = Json.encodeToString(experience).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

suspend fun requestProjectsData(
    onSuccess: (ApiProjectResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val projectsResult = window.api.tryGet(
            apiPath = ApiPaths.READ_PROJECTS_PATH
        )?.decodeToString()
        onSuccess(Json.decodeFromString(projectsResult.toString()))
    } catch (e: Exception) {
        onError(e)
    }

}

suspend fun requestProjectDataById(id: String):ApiProjectResponse{
    return try {
       val selectedProject =  window.api.tryGet(
            apiPath = "${ApiPaths.READ_PROJECTS_BY_ID_PATH}?${Constants.PROJECT_ID_PARAM}=$id",
        )?.decodeToString()
        ApiProjectResponse.Success(data = Json.decodeFromString<List<Project>>(selectedProject.toString()))
    }catch (e:Exception){
        ApiProjectResponse.Error(e.message.toString())
    }
}


suspend fun requestProjectDataUpdate(project: Project):Boolean{
    return try {
         window.api.tryPost(
            apiPath = ApiPaths.UPDATE_PROJECTS_PATH,
            body = Json.encodeToString(project).encodeToByteArray()
        )?.decodeToString().toBoolean()
    }catch (e:Exception){
        println(e.message)
        false
    }
}

suspend fun requestProjectDataAdd(project: Project):Boolean{
    return try {
        window.api.tryPost(
            apiPath = ApiPaths.ADD_PROJECTS_PATH,
            body = Json.encodeToString(project).encodeToByteArray()
        )?.decodeToString().toBoolean()
    }catch (e:Exception){
        println(e.message)
        false
    }
}


suspend fun requestDeletingProjects(ids:List<String>):Boolean{
    return try {
        window.api.tryPost(
            apiPath = ApiPaths.DELETE_PROJECTS_PATH,
            body = Json.encodeToString(ids).encodeToByteArray()
        )?.decodeToString().toBoolean()
    }catch (e:Exception){
        println(e.message)
        false
    }
}





suspend fun requestCertificatesData(
    onSuccess: (ApiCertificateResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val certificatesResult = window.api.tryGet(
            apiPath = ApiPaths.READ_CERTIFICATES_PATH
        )?.decodeToString()
        onSuccess(Json.decodeFromString(certificatesResult.toString()))
    } catch (e: Exception) {
        onError(e)
    }
}





suspend fun requestWebsitesData(
    onSuccess: (ApiWebsiteResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val websitesResult = window.api.tryGet(
            apiPath = ApiPaths.READ_WEBSITE_PATH
        )?.decodeToString()
        onSuccess(Json.decodeFromString(websitesResult.toString()))
    } catch (e: Exception) {
        onError(e)
    }

}


suspend fun requestUserCheck(user: User): UserWithoutPassword? {
    return try {
        val result: ByteArray? = window.api.tryPost(
            apiPath = ApiPaths.USER_CHECK_PATH,
            body = Json.encodeToString(user).encodeToByteArray()
        )
        result?.decodeToString()?.let { Json.decodeFromString<UserWithoutPassword>(it) }
    } catch (e: Exception) {
        println(e.message)
        null
    }
}


suspend fun requestIdCheck(id: String): Boolean {
    return try {
        val result = window.api.tryPost(
            apiPath = ApiPaths.USER_CHECK_ID_PATH,
            body = Json.encodeToString(id).encodeToByteArray()
        )

        result?.decodeToString()?.let {
            Json.decodeFromString<Boolean>(it)
        } ?: false

    } catch (e: Exception) {
        println(e.message.toString())
        false
    }
}

