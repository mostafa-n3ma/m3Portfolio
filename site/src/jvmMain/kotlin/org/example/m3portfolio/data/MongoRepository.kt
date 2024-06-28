package org.example.m3portfolio.data

import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.User
import org.example.m3portfolio.models.Website

interface MongoRepository {

    suspend fun checkUserExistence(user: User):User?
    suspend fun readInfo():List<Info>
    suspend fun readCertificates():List<Certificate>

    suspend fun readProjects():List<Project>

    suspend fun readWebsites():List<Website>
}