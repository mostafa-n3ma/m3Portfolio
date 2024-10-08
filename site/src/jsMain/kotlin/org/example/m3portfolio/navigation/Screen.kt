package org.example.m3portfolio.navigation

import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.CERTIFICATE_ID_PARAM
import org.example.m3portfolio.Constants.EXPERIENCE_ID_PARAM
import org.example.m3portfolio.Constants.GALLERY_ID_PARAM
import org.example.m3portfolio.Constants.PROJECT_ID_PARAM
import org.example.m3portfolio.Constants.WEBSITE_ID_PARAM
import org.example.m3portfolio.Ids

sealed class Screen(val route:String) {
    object Home:Screen(route = "/")


    object AdminHome:Screen(route = "/admin/")
    object AdminLogin:Screen(route = "/admin/login")

    object AdminExperience:Screen(route = "/admin/experience/")

    object AdminExperienceEdit:Screen(route = "/admin/experience/edit"){
        fun passExpId(id:String)= "/admin/experience/edit?${EXPERIENCE_ID_PARAM}=$id"
    }

    object AdminProjects:Screen(route = "/admin/projects/")

    object AdminProjectEdit:Screen(route = "/admin/projects/edit"){
        fun passProjectId(id: String) = "/admin/projects/edit?${PROJECT_ID_PARAM}=$id"
    }




    object AdminCertificates:Screen(route = "/admin/certificates/")
    object AdminCertificatesEdit:Screen(route = "/admin/certificates/edit"){
        fun passCertificateId(id: String) = "/admin/certificates/edit?${CERTIFICATE_ID_PARAM}=$id"
    }


    object AdminWebsites:Screen(route = "/admin/websites/")
    object AdminWebsiteEdit:Screen(route = "/admin/websites/edit"){
        fun passWebsiteId(id: String) = "/admin/websites/edit?$WEBSITE_ID_PARAM=$id"
    }


    object AdminGallery:Screen(route = "/admin/gallery/")






    object ProjectPreview :Screen(route = "/project_preview"){
        fun passProjectId(id:String) = "/project_preview?$PROJECT_ID_PARAM=$id"
    }


    object AboutMe:Screen(route = "/about_me")


}