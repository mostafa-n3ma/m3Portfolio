package org.example.m3portfolio.data

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.example.m3portfolio.Constants.DATABASE_NAME
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.User
import org.example.m3portfolio.models.Website

@InitApi
fun initMongoDb(context: InitApiContext){
    System.setProperty(
        "org.litote.mongo.test.mapping.service",
        "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
    )
    context.data.add(MongoDB(context))
}




class MongoDB(private val context: InitApiContext):MongoRepository {
    private val client = MongoClient.create(
        "mongodb+srv://mostafan3ma:LMqZmLdBXAMR3Dzg@portfoliocluster0.ad5qeri.mongodb.net/"
    )

    val database = client.getDatabase(DATABASE_NAME)
    private val infoCollection = database.getCollection<Info>("info")
    private val certificatesCollection = database.getCollection<Certificate>("certificates")
    private val  projectsCollection = database.getCollection<Project>("projects")
    private val websitesCollection = database.getCollection<Website>("websites")
    private val userCollection = database.getCollection<User>("user")
    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection
                .find(
                    Filters.and(
                        Filters.eq(User::username.name,user.username),
                        Filters.eq(User::password.name,user.password)
                    )
                ).firstOrNull()
        }catch (e:Exception){
            context.logger.error(e.message.toString())
            null
        }
    }

    override suspend fun checkUserId(id: String): Boolean {
        return try {
            userCollection.find(Filters.eq(User::_id.name,id)).count() > 0
        }catch (e:Exception){
            context.logger.error(e.message.toString())
            false
        }
    }


    override suspend fun readInfo(): List<Info> {
        return infoCollection
            .find()
            .toList()
    }

    override suspend fun readCertificates(): List<Certificate> {
        return certificatesCollection
            .find()
            .toList()
    }

    override suspend fun readProjects(): List<Project> {
        return projectsCollection
            .find()
            .toList()
    }

    override suspend fun readWebsites(): List<Website> {
        return websitesCollection
            .find()
            .toList()
    }
}

