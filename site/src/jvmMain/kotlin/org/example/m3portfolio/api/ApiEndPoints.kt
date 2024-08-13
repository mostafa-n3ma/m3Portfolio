package org.example.m3portfolio.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.Data
import com.varabyte.kobweb.api.data.getValue
import io.realm.kotlin.internal.platform.currentTime
import org.bson.codecs.ObjectIdGenerator
import org.example.m3portfolio.ApiPaths
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.EXPERIENCE_ID_PARAM
import org.example.m3portfolio.Constants.VISITOR_ID_PARAM
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
import org.example.m3portfolio.models.Visitor
import org.example.m3portfolio.models.Website
import org.example.m3portfolio.util.getBody
import org.example.m3portfolio.util.setBody
import kotlin.random.Random


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



@Api(routeOverride = ApiPaths.COUNT_VISITORS)
suspend fun getVisitorsCount(context: ApiContext){
    return try {
        val visitorsCount: Int = context.data.getValue<MongoDB>().countVisitors()
        context.res.setBody(visitorsCount)
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = ApiPaths.READ_VISITORS)
suspend fun getAllVisitorsData(context: ApiContext){
    return try {
        val allVisitors: List<Visitor> = context.data.getValue<MongoDB>().readVisitors()
        context.res.setBody(allVisitors)
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = ApiPaths.READ_VISITOR_BY_ID)
suspend fun getVisitorDataById(context: ApiContext){
    println("getVisitorDataById")
    context.logger.info("getVisitorDataById")
        val visitorId = context.req.params[VISITOR_ID_PARAM]
    context.logger.info("getVisitorDataById:visitorId = $visitorId")
    if (!visitorId.isNullOrEmpty()){
        context.logger.info("getVisitorDataById:visitorId is not null  ")
            try {
                val selectedVisitor: List<Visitor> = context.data.getValue<MongoDB>().readVisitorById(id=visitorId)
                context.logger.info("getVisitorDataById:selectedVisitor = $selectedVisitor")
                context.res.setBody(selectedVisitor)

            }catch (e:Exception){
                println("from getVisitorDataById: catching: ${e.message}")
                context.logger.info("getVisitorDataById: catching: ${e.message}")
                context.res.setBody(e.message)
            }
        }else{
        context.logger.info("getVisitorDataById: else: Visitor param empty")
        context.res.setBody(" Visitor param empty")
        }

}

@Api(routeOverride = ApiPaths.RECORD_VISITOR)
suspend fun recordVisitorData(context: ApiContext){
    return try {
        val newVisitor = context.req.getBody<Visitor>()
        val newId = ObjectIdGenerator().generate().toString()
        var result = false
        if (newVisitor!=null){
            result = context.data.getValue<MongoDB>().recordVisitor(newVisitor.copy(_id = newId))
        }

        if (result){
            context.res.setBody(
                newId
            )
        }else{
            context.res.setBody(
                ""
            )
        }
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}


@Api(routeOverride = ApiPaths.UPDATE_VISITOR)
suspend fun updateVisitorData(context: ApiContext){
    return try {
        val updatedVisitor = context.req.getBody<Visitor>()
        context.res.setBody(
            updatedVisitor?.let {
                context.data.getValue<MongoDB>().updateVisitorRecords(it)
            }
        )
    }catch (e:Exception){
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

@Api(routeOverride = ApiPaths.DELETE_EXPERIENCES_PATH)
suspend fun deleteExperiences(context: ApiContext){
    return try {
        val deletedExperiences = context.req.getBody<List<String>>()
        context.res.setBody(
            deletedExperiences?.let {
                context.data.getValue<MongoDB>().deleteSelectedExperiences(it)
            }
        )
    }catch (e:Exception){
        context.res.setBody(
            e.message
        )
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


@Api(routeOverride = ApiPaths.READ_CERTIFICATES_BY_ID_PATH)
suspend fun getCertificateById(context: ApiContext){
    try {
        val id = context.req.params[Constants.CERTIFICATE_ID_PARAM]
        if (!id.isNullOrEmpty()){
            val selectedCertificate = context.data.getValue<MongoDB>().readCertificatesById(id)
            context.res.setBody(selectedCertificate)
        }else{
            context.res.setBody(
                "Error Certificate does not exist"
            )
        }
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}



@Api(routeOverride = ApiPaths.UPDATE_CERTIFICATES_PATH)
suspend fun updateCertificateData(context: ApiContext){
    return try {
        val updatedCertificate = context.req.getBody<Certificate>()
        context.res.setBody(
            updatedCertificate?.let {
                context.data.getValue<MongoDB>().updateCertificate(it)
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}


@Api(routeOverride = ApiPaths.ADD_CERTIFICATES_PATH)
suspend fun addCertificateData(context: ApiContext){
    return try {
        val newCertificate = context.req.getBody<Certificate>()
        context.res.setBody(
            newCertificate?.let {
                context.data.getValue<MongoDB>().insertCertificate(it.copy(_id = ObjectIdGenerator().generate().toString()))
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = ApiPaths.DELETE_CERTIFICATES_PATH)
suspend fun deleteSelectedCertificatesData(context: ApiContext){
    return try{
        val deletedCertificatesIdsList = context.req.getBody<List<String>>()
        context.res.setBody(
            deletedCertificatesIdsList?.let {
                context.data.getValue<MongoDB>().deleteSelectedCertificates(it)
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
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

@Api(routeOverride = ApiPaths.READ_WEBSITE_BY_ID_PATH)
suspend fun getWebsiteById(context: ApiContext){
    try {
        val id = context.req.params[Constants.WEBSITE_ID_PARAM]
        if (!id.isNullOrEmpty()){
            val selectedWebsite = context.data.getValue<MongoDB>().readWebsitesById(id)
            context.res.setBody(selectedWebsite)
        }else{
            context.res.setBody("Error website does not Exist")
        }
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}



@Api(routeOverride = ApiPaths.UPDATE_WEBSITE_PATH)
suspend fun updateWebsiteData(context: ApiContext){
    return try {
        val updatedWebsite = context.req.getBody<Website>()
        context.res.setBody(
            updatedWebsite?.let {
                context.data.getValue<MongoDB>().updateWebsite(it)
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}


@Api(routeOverride = ApiPaths.ADD_WEBSITE_PATH)
suspend fun addWebsiteData(context: ApiContext){
    return try {
        val newWebsite = context.req.getBody<Website>()
        context.res.setBody(
            newWebsite?.let {
                context.data.getValue<MongoDB>().insertWebsite(it.copy(_id = ObjectIdGenerator().generate().toString()))
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}



@Api(routeOverride = ApiPaths.DELETE_WEBSITES_PATH)
suspend fun deleteWebsiteData(context: ApiContext){
    return try {
        val deletedWebsitesIdsList = context.req.getBody<List<String>>()
        context.res.setBody(
            deletedWebsitesIdsList?.let {
                context.data.getValue<MongoDB>().deleteSelectedWebsites(it)
            }
        )
    }catch (e:Exception){
        context.res.setBody(e.message)
    }
}





