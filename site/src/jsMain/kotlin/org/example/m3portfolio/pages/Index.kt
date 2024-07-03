package org.example.m3portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import kotlinx.coroutines.launch
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.User
import org.example.m3portfolio.util.requestCertificatesData
import org.example.m3portfolio.util.requestInfoData
import org.example.m3portfolio.util.requestProjectsData
import org.example.m3portfolio.util.requestUserCheck
import org.example.m3portfolio.util.requestWebsitesData
import org.jetbrains.compose.web.dom.Text

@Page()
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("\nm3protfolio 99 بسم الله الرحمن الرحيم")
        scope.launch {
//            println("entering the scope //projects")
//            requestInfoData(
//                onSuccess = {respons:ApiInfoResponse->
//                            when(respons){
//                                is ApiInfoResponse.Error -> {
//                                    println("Info: Error")
//                                    println(respons.message)
//                                }
//                                ApiInfoResponse.Idle -> println("Info: Response stile Ideal")
//                                is ApiInfoResponse.Success -> {
//                                    println("Info: success")
//                                    println(respons.data.toString())
//                                }
//                            }
//                },
//                onError = {
//                    println(it.message)
//                }
//            )
//
//
//            requestCertificatesData(
//                onSuccess = {response->
//                            when(response){
//                                is ApiCertificateResponse.Error -> {
//                                    println("Certificates: Error")
//                                    println(response.message)
//                                }
//                                ApiCertificateResponse.Idle -> println("Certificates: Response stile Ideal")
//                                is ApiCertificateResponse.Success -> {
//                                    println("Certificates: success")
//                                    println(response.data.toString())
//                                }
//                            }
//                },
//                onError = {
//                    println(it.message)
//                }
//            )
//
//
//            requestProjectsData(
//                onSuccess = {response->
//                            when(response){
//                                is ApiProjectResponse.Error -> {
//                                    println("projects: Error")
//                                    println(response.message)
//                                }
//                                ApiProjectResponse.Idle -> println("projects: Response stile Ideal")
//                                is ApiProjectResponse.Success ->{
//                                    println("projects: success")
//                                    println(response.data)
//                                }
//                            }
//
//                },
//                onError = {
//                    println(it.message)
//                }
//            )
//
//
//
//            requestWebsitesData(
//                onSuccess = {response->
//                    when(response){
//                        is ApiWebsiteResponse.Error -> {
//                            println("websites: Error")
//                            println(response.message)
//                        }
//                        ApiWebsiteResponse.Idle -> println("websites: Response stile Ideal")
//                        is ApiWebsiteResponse.Success ->{
//                            println("websites: success")
//                            println(response.data)
//                        }
//                    }
//
//                },
//                onError = {
//                    println(it.message)
//                }
//            )
//






        }


    }
}
