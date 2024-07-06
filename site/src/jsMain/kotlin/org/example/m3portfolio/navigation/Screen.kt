package org.example.m3portfolio.navigation

sealed class Screen(val route:String) {
    object AdminHome:Screen(route = "/admin/")
    object AdminLogin:Screen(route = "/admin/login")

    object AdminInfo:Screen(route = "/admin/info")
    object AdminExperience:Screen(route = "/admin/experience")
    object AdminProjects:Screen(route = "/admin/projects")
    object AdminCertificates:Screen(route = "/admin/certificates")
    object AdminWebsites:Screen(route = "/admin/websites")






}