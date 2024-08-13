package org.example.m3portfolio.data

import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.User
import org.example.m3portfolio.models.Visitor
import org.example.m3portfolio.models.Website

interface MongoRepository {

    suspend fun checkUserExistence(user: User):User?
    suspend fun checkUserId(id:String):Boolean



    suspend fun countVisitors():Int
    suspend fun readVisitors():List<Visitor>
    suspend fun readVisitorById(id: String):List<Visitor>
    suspend fun recordVisitor(visitor: Visitor):Boolean

    suspend fun updateVisitorRecords(visitor: Visitor):Boolean





    suspend fun readInfo():List<Info>
    suspend fun updateInfo(info: Info):Boolean

    suspend fun readExperience():List<Experience>
    suspend fun readExperienceById(id:String):List<Experience>
    suspend fun insertExperience(experience: Experience):Boolean
    suspend fun updateExperience(experience: Experience):Boolean
    suspend fun deleteSelectedExperiences(ids:List<String>):Boolean


    suspend fun readProjects():List<Project>
    suspend fun readProjectById(id: String):List<Project>
    suspend fun insertProject(project: Project):Boolean
    suspend fun updateProject(project: Project):Boolean
    suspend fun deleteSelectedProjects(ids: List<String>):Boolean


    suspend fun readCertificates():List<Certificate>
    suspend fun readCertificatesById(id:String):List<Certificate>
    suspend fun insertCertificate(certificate: Certificate):Boolean
    suspend fun updateCertificate(certificate: Certificate):Boolean
    suspend fun deleteSelectedCertificates(ids:List<String>):Boolean


    suspend fun readWebsites():List<Website>
    suspend fun readWebsitesById(id:String):List<Website>
    suspend fun insertWebsite(website: Website):Boolean
    suspend fun updateWebsite(website: Website):Boolean
    suspend fun deleteSelectedWebsites(ids:List<String>):Boolean







}