package org.example.m3portfolio.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import org.example.m3portfolio.ApiPaths
import org.example.m3portfolio.data.MongoDB
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Website
import org.example.m3portfolio.util.setBody


@Api(routeOverride = ApiPaths.READ_INFO_PATH)
suspend fun getInfoData(context: ApiContext){
    try {
        val infoList: List<Info> = context.data.getValue<MongoDB>().readInfo()
        context.res.setBody(ApiInfoResponse.Success(data = infoList[0]))

    }catch (e:Exception){
        context.res.setBody(ApiInfoResponse.Error(e.message.toString()))

    }
}




@Api(routeOverride = ApiPaths.READ_CERTIFICATES_PATH)
suspend fun getCertificatesData(context: ApiContext){
    try {
        val certificatesResult: List<Certificate> = context.data.getValue<MongoDB>().readCertificates()
        context.logger.info("certificates from mongo read :$certificatesResult")
        context.res.setBody(ApiCertificateResponse.Success(data = certificatesResult))

    }catch (e:Exception){
        context.res.setBody(ApiCertificateResponse.Error(e.message.toString()))
    }
}


@Api(routeOverride = ApiPaths.READ_PROJECTS_PATH)
suspend fun getProjectsData(context: ApiContext){
    try {
        val projectsResult: List<Project> = context.data.getValue<MongoDB>().readProjects()
        context.res.setBody(ApiProjectResponse.Success(data = projectsResult))
    }catch (e:Exception){
        context.res.setBody(ApiProjectResponse.Error(e.message.toString()))
    }
}


@Api(routeOverride = ApiPaths.READ_WEBSITE_PATH)
suspend fun getWebsitesData(context: ApiContext){
    try {
        val websitesResult: List<Website> = context.data.getValue<MongoDB>().readWebsites()
        context.res.setBody(ApiWebsiteResponse.Success(data = websitesResult))

    }catch (e:Exception){
        context.res.setBody(ApiWebsiteResponse.Error(e.message.toString()))
    }
}




