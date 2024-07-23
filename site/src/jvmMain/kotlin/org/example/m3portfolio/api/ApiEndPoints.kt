package org.example.m3portfolio.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import org.bson.codecs.ObjectIdGenerator
import org.example.m3portfolio.ApiPaths
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.EXPERIENCE_ID_PARAM
import org.example.m3portfolio.data.MongoDB
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.ApiExperienceResponse
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Website
import org.example.m3portfolio.util.getBody
import org.example.m3portfolio.util.setBody


@Api(routeOverride = ApiPaths.READ_INFO_PATH)
suspend fun getInfoData(context: ApiContext) {
    try {
        val infoList: List<Info> = context.data.getValue<MongoDB>().readInfo()
        context.res.setBody(ApiInfoResponse.Success(data = infoList[0]))

    } catch (e: Exception) {
        context.res.setBody(ApiInfoResponse.Error(e.message.toString()))

    }
}


@Api(routeOverride = ApiPaths.UPDATE_INFO_PATH)
suspend fun updateInfoData(context: ApiContext) {
    try {
        val updatedInfo = context.req.getBody<Info>()
        context.res.setBody(updatedInfo?.let { context.data.getValue<MongoDB>().updateInfo(it) })
    } catch (e: Exception) {
        context.res.setBody(e.message)
    }
}





@Api(routeOverride = ApiPaths.READ_EXPERIENCE_PATH)
suspend fun getExperienceData(context: ApiContext){
    try {
        val experiences = context.data.getValue<MongoDB>().readExperience()
        context.res.setBody(ApiExperienceResponse.Success(data = experiences))

    }catch (e:Exception){
        context.res.setBody(ApiExperienceResponse.Error(message = e.message.toString()))
    }
}

@Api(routeOverride = ApiPaths.READ_EXPERIENCE_BY_ID_PATH)
suspend fun getExperienceDataById(context: ApiContext){
    val expId = context.req.params[EXPERIENCE_ID_PARAM]
    if (!expId.isNullOrEmpty()){
        try {
            val selectedExp: List<Experience> = context.data.getValue<MongoDB>().readExperienceById(id = expId)
            println("from getExperienceDataById: selectedExp=$selectedExp")
            context.res.setBody(selectedExp)
        }catch (e:Exception){
            println("from getExperienceDataById: catching: ${e.message}")
            context.res.setBody(e.message)
        }
    }else{
        context.res.setBody("no Experience available")
    }

}



@Api(routeOverride = ApiPaths.UPDATE_EXPERIENCE_PATH)
suspend fun updateExperienceData(context: ApiContext){
    try {
        val updatedExperience = context.req.getBody<Experience>()
        context.res.setBody(
            updatedExperience?.let { context.data.getValue<MongoDB>().updateExperience(it) }
        )

    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = ApiPaths.ADD_EXPERIENCE_PATH)
suspend fun addExperienceData(context: ApiContext){
    try {
        val experience = context.req.getBody<Experience>()
        val newExperience = experience?.copy(_id = ObjectIdGenerator().generate().toString())
        context.res.setBody(
            newExperience?.let {
                context.data.getValue<MongoDB>().insertExperience(it)
            }
        )

    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = ApiPaths.READ_PROJECTS_PATH)
suspend fun getProjectsData(context: ApiContext) {
    try {
        val projectsResult: List<Project> = context.data.getValue<MongoDB>().readProjects()
        context.res.setBody(ApiProjectResponse.Success(data = projectsResult))
    } catch (e: Exception) {
        context.res.setBody(ApiProjectResponse.Error(e.message.toString()))
    }
}


@Api(routeOverride = ApiPaths.READ_PROJECTS_BY_ID_PATH)
suspend fun getProjectDataById(context: ApiContext){
    return try {
        val id = context.req.params[Constants.PROJECT_ID_PARAM]
        if (!id.isNullOrEmpty()){
            val selectedProject = context.data.getValue<MongoDB>().readProjectById(id)
            context.res.setBody(selectedProject)
        }else{
            context.res.setBody("project does not exist")
        }

    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = ApiPaths.UPDATE_PROJECTS_PATH)
suspend fun updateProjectData(context: ApiContext){
    return try {
        val updatedProject = context.req.getBody<Project>()
        context.res.setBody(
            updatedProject?.let {
                context.data.getValue<MongoDB>().updateProject(it)
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}


@Api(ApiPaths.ADD_PROJECTS_PATH)
suspend fun addProjectData(context: ApiContext){
    return try {
        val newProject = context.req.getBody<Project>()
        context.res.setBody(
            newProject?.let {
                context.data.getValue<MongoDB>().insertProject(it.copy(_id = ObjectIdGenerator().generate().toString()))
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}


@Api(routeOverride = ApiPaths.DELETE_PROJECTS_PATH)
suspend fun deleteSelectedProjectsData(context: ApiContext){
    return try {
        val deletedProjectsIdsList =  context.req.getBody<List<String>>()
        context.res.setBody(
            deletedProjectsIdsList?.let {
                context.data.getValue<MongoDB>().deleteSelectedProjects(deletedProjectsIdsList)
            }
        )

    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}




@Api(routeOverride = ApiPaths.READ_CERTIFICATES_PATH)
suspend fun getCertificatesData(context: ApiContext) {
    try {
        val certificatesResult: List<Certificate> =
            context.data.getValue<MongoDB>().readCertificates()
        context.logger.info("certificates from mongo read :$certificatesResult")
        context.res.setBody(ApiCertificateResponse.Success(data = certificatesResult))

    } catch (e: Exception) {
        context.res.setBody(ApiCertificateResponse.Error(e.message.toString()))
    }
}





@Api(routeOverride = ApiPaths.READ_WEBSITE_PATH)
suspend fun getWebsitesData(context: ApiContext) {
    try {
        val websitesResult: List<Website> = context.data.getValue<MongoDB>().readWebsites()
        context.res.setBody(ApiWebsiteResponse.Success(data = websitesResult))

    } catch (e: Exception) {
        context.res.setBody(ApiWebsiteResponse.Error(e.message.toString()))
    }
}




