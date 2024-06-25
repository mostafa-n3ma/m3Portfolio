package org.example.m3portfolio.models

import kotlinx.serialization.SerialName
import org.bson.codecs.ObjectIdGenerator

actual data class User(
    @SerialName(value = "_id")
    actual val _id:String = ObjectIdGenerator().generate().toString(),
    actual val username:String,
    actual val password:String
)



actual data class UserWithoutPassword(
    @SerialName(value = "_id")
    actual val _id: String=ObjectIdGenerator().generate().toString(),
    actual val username:String
)
