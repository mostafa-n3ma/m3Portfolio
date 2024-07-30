package org.example.m3portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.example.m3portfolio.components.MainHeaderPanel
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Info
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Website
import org.example.m3portfolio.sections.CertificatesSection
import org.example.m3portfolio.sections.MainSection
import org.example.m3portfolio.sections.ProjectsSection
import org.example.m3portfolio.sections.SkillsSection
import org.example.m3portfolio.util.requestCertificatesData
import org.example.m3portfolio.util.requestInfoData
import org.example.m3portfolio.util.requestProjectsData
import org.example.m3portfolio.util.requestWebsitesData


data class BigObjectUiState(
    var infoObject: Info=Info(),
    var experiencesList: List<Experience> = listOf(),
    var projectsList: List<Project> = listOf(),
    var certificatesList: List<Certificate> = listOf(),
    var websitesList: List<Website> = listOf(),
    var messagePopup:String = ""
)

@Page()
@Composable
fun HomePage() {
    val breakpoint = rememberBreakpoint()
    val bigObject = remember { mutableStateOf(BigObjectUiState()) }
    val context = rememberPageContext()
//    breakpoint.let {
//        bigObject.value.messagePopup = it.name
//    }

    if (bigObject.value.messagePopup.isNotEmpty()) {
        MessagePopup(
            message = bigObject.value.messagePopup,
            onDialogDismiss = {
                bigObject.value = bigObject.value.copy(messagePopup = "")
            }
        )
    }

    fetchApiData(bigObject = bigObject)

    MainHeaderPanel {
        MainSection(breakpoint = breakpoint,bigObject = bigObject.value,context = context )
        ProjectsSection(breakpoint = breakpoint,bigObject = bigObject.value,context = context )
        CertificatesSection(breakpoint = breakpoint,bigObject = bigObject.value ,context = context)
        SkillsSection(breakpoint = breakpoint,bigObject = bigObject.value)
    }
}

@Composable
fun fetchApiData(bigObject: MutableState<BigObjectUiState>) {
    val scope = rememberCoroutineScope()
    scope.launch {
        println("entering the scope //projects")
        requestInfoData(
            onSuccess = {respons: ApiInfoResponse ->
                when(respons){
                    is ApiInfoResponse.Error -> {
                        println("Info: Error")
                        println(respons.message)
                    }
                    ApiInfoResponse.Idle -> println("Info: Response stile Ideal")
                    is ApiInfoResponse.Success -> {
                        println("Info: success")
//                        println(respons.data.toString())
                        bigObject.value = bigObject.value.copy(
                            infoObject = respons.data
                        )
                    }
                }
            },
            onError = {
                println(it.message)
            }
        )


        requestCertificatesData(
            onSuccess = {response->
                when(response){
                    is ApiCertificateResponse.Error -> {
                        println("Certificates: Error")
                        println(response.message)
                    }
                    ApiCertificateResponse.Idle -> println("Certificates: Response stile Ideal")
                    is ApiCertificateResponse.Success -> {
                        println("Certificates: success")
//                        println(response.data.toString())
                        bigObject.value = bigObject.value.copy(
                            certificatesList = response.data
                        )
                    }
                }
            },
            onError = {
                println(it.message)
            }
        )


        requestProjectsData(
            onSuccess = {response->
                when(response){
                    is ApiProjectResponse.Error -> {
                        println("projects: Error")
                        println(response.message)
                    }
                    ApiProjectResponse.Idle -> println("projects: Response stile Ideal")
                    is ApiProjectResponse.Success ->{
                        println("projects: success")
//                        println(response.data)
                        bigObject.value = bigObject.value.copy(
                            projectsList = response.data
                        )
                    }
                }

            },
            onError = {
                println(it.message)
            }
        )



        requestWebsitesData(
            onSuccess = {response->
                when(response){
                    is ApiWebsiteResponse.Error -> {
                        println("websites: Error")
                        println(response.message)
                    }
                    ApiWebsiteResponse.Idle -> println("websites: Response stile Ideal")
                    is ApiWebsiteResponse.Success ->{
                        println("websites: success")
//                        println(response.data)
                    }
                }

            },
            onError = {
                println(it.message)
            }
        )



    }

}





