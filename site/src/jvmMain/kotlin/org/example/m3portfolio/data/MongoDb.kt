package org.example.m3portfolio.data

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.example.m3portfolio.Constants.DATABASE_NAME
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.User
import org.example.m3portfolio.models.Visitor
import org.example.m3portfolio.models.Website

@InitApi
fun initMongoDb(context: InitApiContext) {
    System.setProperty(
        "org.litote.mongo.test.mapping.service",
        "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
    )
    context.data.add(MongoDB(context))
}


class MongoDB(private val context: InitApiContext) : MongoRepository {
    private val client = MongoClient.create(
        "mongodb+srv://mostafan3ma:LMqZmLdBXAMR3Dzg@portfoliocluster0.ad5qeri.mongodb.net/"
    )

    val database = client.getDatabase(DATABASE_NAME)
    private val infoCollection = database.getCollection<Info>("info")
    private val experienceCollection = database.getCollection<Experience>("experiances")
    private val certificatesCollection = database.getCollection<Certificate>("certificates")
    private val projectsCollection = database.getCollection<Project>("projects")
    private val websitesCollection = database.getCollection<Website>("websites")
    private val userCollection = database.getCollection<User>("user")
    private val visitorsCollection = database.getCollection<Visitor>("visitors")
    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection
                .find(
                    Filters.and(
                        Filters.eq(User::username.name, user.username),
                        Filters.eq(User::password.name, user.password)
                    )
                ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            null
        }
    }

    override suspend fun checkUserId(id: String): Boolean {
        return try {
            userCollection.find(Filters.eq(User::_id.name, id)).count() > 0
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            false
        }
    }

    override suspend fun countVisitors(): Int {
        return visitorsCollection
            .find()
            .toList()
            .count()
    }

    override suspend fun readVisitors(): List<Visitor> {
        return visitorsCollection
            .find()
            .toList()
    }

    override suspend fun readVisitorById(id: String): List<Visitor> {
        context.logger.info("readVisitorById//MongoDb// id:$id")
        val result: List<Visitor> = visitorsCollection
            .find(
                Filters.eq(Visitor::_id.name,id)
            ).toList()
        context.logger.info("readVisitorById//MongoDb// resultList:$result")
        return result
    }

    override suspend fun recordVisitor(visitor: Visitor): Boolean {
       return visitorsCollection
           .insertOne(visitor)
           .wasAcknowledged()
    }

    override suspend fun updateVisitorRecords(visitor: Visitor): Boolean {
       return visitorsCollection
           .updateOne(
               Filters.eq(Visitor::_id.name,visitor._id),
               mutableListOf(
                   Updates.set(Visitor::date.name,visitor.date)
               )
           ).wasAcknowledged()
    }


    override suspend fun readInfo(): List<Info> {
        return infoCollection
            .find()
            .toList()
    }

    override suspend fun updateInfo(info: Info): Boolean {
        return infoCollection
            .updateOne(
                Filters.eq(Info::_id.name, info._id),
                mutableListOf(
                    Updates.set(Info::name.name, info.name),
                    Updates.set(Info::imageUrl.name, info.imageUrl),
                    Updates.set(Info::role.name, info.role),
                    Updates.set(Info::address.name, info.address),
                    Updates.set(Info::phone.name, info.phone),
                    Updates.set(Info::email.name, info.email),
                    Updates.set(Info::linkedIn.name, info.linkedIn),
                    Updates.set(Info::github.name, info.github),
                    Updates.set(Info::bio.name, info.bio),
                    Updates.set(Info::education.name, info.education),
                    Updates.set(Info::skills.name, info.skills),
                    Updates.set(Info::programLanguages.name, info.programLanguages),
                    Updates.set(Info::tools.name, info.tools),
                    Updates.set(Info::frameWorks.name, info.frameWorks),
                    Updates.set(Info::resumeLink.name, info.resumeLink),
                    Updates.set(Info::extra.name, info.extra)
                )
            ).wasAcknowledged()
    }

    override suspend fun readExperience(): List<Experience> {
        return experienceCollection
            .find()
            .toList()
    }

    override suspend fun readExperienceById(id: String): List<Experience> {
        return experienceCollection
            .find(
                Filters.eq(Experience::_id.name, id)
            )
            .toList()
    }

    override suspend fun insertExperience(experience: Experience): Boolean {
        return experienceCollection
            .insertOne(experience)
            .wasAcknowledged()
    }

    override suspend fun updateExperience(experience: Experience): Boolean {
        return experienceCollection
            .updateOne(
                Filters.eq(Experience::_id.name, experience._id),
                mutableListOf(
                    Updates.set(Experience::description.name, experience.description),
                    Updates.set(Experience::duration.name, experience.duration),
                    Updates.set(Experience::image.name, experience.image),
                    Updates.set(Experience::location.name, experience.location),
                    Updates.set(Experience::projects.name, experience.projects),
                    Updates.set(Experience::role.name, experience.role),
                )
            ).wasAcknowledged()
    }

    override suspend fun deleteSelectedExperiences(ids: List<String>): Boolean {
        return experienceCollection
            .deleteMany(Filters.`in`(Experience::_id.name,ids)).wasAcknowledged()
    }


    override suspend fun readProjects(): List<Project> {
        return projectsCollection
            .find()
            .toList()
    }

    override suspend fun readProjectById(id: String): List<Project> {
        return projectsCollection
            .find(
                Filters.eq(Project::_id.name,id)
            )
            .toList()
    }

    override suspend fun insertProject(project: Project): Boolean {
       return projectsCollection
           .insertOne(project)
           .wasAcknowledged()
    }

    override suspend fun updateProject(project: Project): Boolean {
        return projectsCollection
            .updateOne(
                Filters.eq(Project::_id.name,project._id),
                mutableListOf(
                    Updates.set(Project:: title.name,project.title),
                    Updates.set(Project:: subTitle.name,project.subTitle),
                    Updates.set(Project:: description.name,project.description),
                    Updates.set(Project:: techStack.name,project.techStack),
                    Updates.set(Project:: repoLink.name,project.repoLink),
                    Updates.set(Project:: videoLink.name,project.videoLink),
                    Updates.set(Project:: mainImageLink.name,project.mainImageLink),
                    Updates.set(Project:: imagesList.name,project.imagesList),
                    Updates.set(Project:: date.name,project.date),
                )
            ).wasAcknowledged()
    }

    override suspend fun deleteSelectedProjects(ids: List<String>): Boolean {
        return projectsCollection
            .deleteMany(Filters.`in`(Project::_id.name,ids))
            .wasAcknowledged()
    }


    override suspend fun readCertificates(): List<Certificate> {
        return certificatesCollection
            .find()
            .toList()
    }

    override suspend fun readCertificatesById(id: String): List<Certificate> {
        return certificatesCollection
            .find(
                Filters.eq(Certificate::_id.name,id)
            )
            .toList()
    }

    override suspend fun insertCertificate(certificate: Certificate): Boolean {
        return certificatesCollection
            .insertOne(certificate)
            .wasAcknowledged()
    }

    override suspend fun updateCertificate(certificate: Certificate): Boolean {
        return certificatesCollection
            .updateOne(
                Filters.eq(Certificate::_id.name,certificate._id),
                mutableListOf(
                    Updates.set(Certificate::title.name,certificate.title),
                    Updates.set(Certificate::from.name,certificate.from),
                    Updates.set(Certificate::link.name,certificate.link),
                    Updates.set(Certificate::date.name,certificate.date),
                    Updates.set(Certificate::thumbnailLink.name,certificate.thumbnailLink),
                )
            ).wasAcknowledged()
    }

    override suspend fun deleteSelectedCertificates(ids: List<String>): Boolean {
        return certificatesCollection
            .deleteMany(Filters.`in`(Certificate::_id.name,ids)).wasAcknowledged()
    }


    override suspend fun readWebsites(): List<Website> {
        return websitesCollection
            .find()
            .toList()
    }

    override suspend fun readWebsitesById(id: String): List<Website> {
        context.logger.info("readWebsitesById//MongoDb// id:$id")
        val result =  websitesCollection
            .find(
                Filters.eq(Website::_id.name,id)
            )
            .toList()
        context.logger.info("readWebsitesById//MongoDb// resultList:$result")
        return result
    }

    override suspend fun insertWebsite(website: Website): Boolean {
        return websitesCollection
            .insertOne(website)
            .wasAcknowledged()
    }

    override suspend fun updateWebsite(website: Website): Boolean {
        return websitesCollection
            .updateOne(
                Filters.eq(Website::_id.name,website._id),
                mutableListOf(
                    Updates.set(Website::title.name,website.title),
                    Updates.set(Website::link.name,website.link),
                    Updates.set(Website::icon.name,website.icon),
                )
            ).wasAcknowledged()
    }

    override suspend fun deleteSelectedWebsites(ids: List<String>): Boolean {
        return websitesCollection
            .deleteMany(Filters.`in`(Website::_id.name,ids)).wasAcknowledged()
    }
}

