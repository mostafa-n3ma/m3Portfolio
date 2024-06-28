package org.example.m3portfolio.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.m3portfolio.ApiPaths
import org.example.m3portfolio.data.MongoDB
import org.example.m3portfolio.models.User
import org.example.m3portfolio.models.UserWithoutPassword
import org.example.m3portfolio.util.setBody
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


@Api(routeOverride = ApiPaths.USER_CHECK_PATH)
suspend fun userCheck(context: ApiContext){
    try {
        // get the user from the post request body
        val requestedUser =  context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }

        context.logger.error("UserCheckPoint: requestedUser=$requestedUser")
        // checking the user with the database and returning a user object when it matches or null when not
        val user: User? = requestedUser?.let {
            context.data.getValue<MongoDB>().checkUserExistence(
                User(username = it.username, password = hashPassword(it.password))
            )
        }
        context.logger.error("UserCheckPoint: user from Mongo :$user")
        // if the user is not null return a body text as userWithoutPassword object
        if (user!=null){
            context.res.setBodyText(
                Json.encodeToString<UserWithoutPassword>(
                    UserWithoutPassword(_id = user._id, username = user.username)
                )
            )
        }else{
            context.logger.error("UserCheckPoint: user is null :User doesn't exist")
            context.res.setBodyText(Json.encodeToString(Exception("User doesn't exist.")))
        }

    }catch (e:Exception){
        context.logger.error(e.message.toString())
    }
}



private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }

    return hexString.toString()
}

