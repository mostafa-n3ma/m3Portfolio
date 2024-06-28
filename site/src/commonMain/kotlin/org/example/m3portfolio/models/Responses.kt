package org.example.m3portfolio.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject


@Serializable(ApiInfoResponseSerializer::class)
sealed class ApiInfoResponse {
    @Serializable
    @SerialName("idle")
    object Idle:ApiInfoResponse()

    @Serializable
    @SerialName("success")
    class Success(val data: Info):ApiInfoResponse()

    @Serializable
    @SerialName("error")
    class Error(val message:String):ApiInfoResponse()
}


object ApiInfoResponseSerializer: JsonContentPolymorphicSerializer<ApiInfoResponse>(ApiInfoResponse::class){
    override fun selectDeserializer(element: JsonElement)= when{
        "data" in element.jsonObject -> ApiInfoResponse.Success.serializer()
        "message" in element.jsonObject ->ApiInfoResponse.Error.serializer()
        else-> ApiInfoResponse.Idle.serializer()
    }

}



@Serializable(ApiProjectResponseSerializer::class)
sealed class ApiProjectResponse {
    @Serializable
    @SerialName("idle")
    object Idle:ApiProjectResponse()

    @Serializable
    @SerialName("success")
    class Success(val data: List<Project>):ApiProjectResponse()

    @Serializable
    @SerialName("error")
    class Error(val message:String):ApiProjectResponse()
}


object ApiProjectResponseSerializer: JsonContentPolymorphicSerializer<ApiProjectResponse>(ApiProjectResponse::class){
    override fun selectDeserializer(element: JsonElement)= when{
        "data" in element.jsonObject -> ApiProjectResponse.Success.serializer()
        "message" in element.jsonObject ->ApiProjectResponse.Error.serializer()
        else-> ApiProjectResponse.Idle.serializer()
    }

}





@Serializable(ApiCertificateResponseSerializer::class)
sealed class ApiCertificateResponse {
    @Serializable
    @SerialName("idle")
    object Idle:ApiCertificateResponse()

    @Serializable
    @SerialName("success")
    class Success(val data: List<Certificate>):ApiCertificateResponse()

    @Serializable
    @SerialName("error")
    class Error(val message:String):ApiCertificateResponse()
}


object ApiCertificateResponseSerializer: JsonContentPolymorphicSerializer<ApiCertificateResponse>(ApiCertificateResponse::class){
    override fun selectDeserializer(element: JsonElement)= when{
        "data" in element.jsonObject -> ApiCertificateResponse.Success.serializer()
        "message" in element.jsonObject ->ApiCertificateResponse.Error.serializer()
        else-> ApiCertificateResponse.Idle.serializer()
    }

}






@Serializable(ApiWebsiteResponseSerializer::class)
sealed class ApiWebsiteResponse {
    @Serializable
    @SerialName("idle")
    object Idle:ApiWebsiteResponse()

    @Serializable
    @SerialName("success")
    class Success(val data: List<Website>):ApiWebsiteResponse()

    @Serializable
    @SerialName("error")
    class Error(val message:String):ApiWebsiteResponse()
}


object ApiWebsiteResponseSerializer: JsonContentPolymorphicSerializer<ApiWebsiteResponse>(ApiWebsiteResponse::class){
    override fun selectDeserializer(element: JsonElement)= when{
        "data" in element.jsonObject -> ApiWebsiteResponse.Success.serializer()
        "message" in element.jsonObject ->ApiWebsiteResponse.Error.serializer()
        else-> ApiWebsiteResponse.Idle.serializer()
    }

}
