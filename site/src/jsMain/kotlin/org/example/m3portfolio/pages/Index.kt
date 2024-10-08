package org.example.m3portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.LANGUAGE_STORAGE_VALUE
import org.example.m3portfolio.Constants.VISITOR_ID
import org.example.m3portfolio.components.MainHeaderPanel
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.getCurrentDateTime
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.ApiExperienceResponse
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.Visitor
import org.example.m3portfolio.sections.CertificatesSection
import org.example.m3portfolio.sections.ExperienceSection
import org.example.m3portfolio.sections.FooterSection
import org.example.m3portfolio.sections.MainSection
import org.example.m3portfolio.sections.ProjectsSection
import org.example.m3portfolio.sections.SkillsSection
import org.example.m3portfolio.util.BigObjectUiState
import org.example.m3portfolio.util.requestCertificatesData
import org.example.m3portfolio.util.requestExperienceData
import org.example.m3portfolio.util.requestInfoData
import org.example.m3portfolio.util.requestProjectsData
import org.example.m3portfolio.util.requestVisitorAdd
import org.example.m3portfolio.util.requestVisitorById
import org.example.m3portfolio.util.requestVisitorUpdate
import org.example.m3portfolio.util.requestVisitorsCount
import org.example.m3portfolio.util.requestVisitorsData
import org.example.m3portfolio.util.requestWebsitesData
import org.example.m3portfolio.util.splitLanguages
import org.w3c.dom.get
import org.w3c.dom.set


@Page()
@Composable
fun HomePage() {
    val breakpoint = rememberBreakpoint()
    val bigObject = remember { mutableStateOf(BigObjectUiState()) }
    val context = rememberPageContext()




    val displayLanguage: MutableState<Constants.Languages> = remember(
        window.localStorage[LANGUAGE_STORAGE_VALUE]
    ) { mutableStateOf(Constants.Languages.valueOf(localStorage[LANGUAGE_STORAGE_VALUE] ?: "EN")) }




    if (bigObject.value.messagePopup.isNotEmpty()) {
        MessagePopup(
            message = bigObject.value.messagePopup,
            onDialogDismiss = {
                bigObject.value = bigObject.value.copy(messagePopup = "")
            }
        )
    }

    fetchApiData(bigObject = bigObject)
    println(bigObject.value.experiencesList)

    MainHeaderPanel(
        displayLanguage = displayLanguage,
        onLanguageChange = {
            displayLanguage.value = it
        }
    ) {
        MainSection(
            breakpoint = breakpoint,
            bigObject = bigObject.value.splitLanguages(displayLanguage.value),
            context = context,
            displayLanguage = displayLanguage
        )
        ProjectsSection(
            breakpoint = breakpoint,
            bigObject = bigObject.value.splitLanguages(displayLanguage.value),
            context = context,
            displayLanguage = displayLanguage
        )
        CertificatesSection(
            breakpoint = breakpoint,
            bigObject = bigObject.value.splitLanguages(displayLanguage.value),
            context = context,
            displayLanguage = displayLanguage
        )
        SkillsSection(
            breakpoint = breakpoint,
            bigObject = bigObject.value.splitLanguages(displayLanguage.value),
            displayLanguage = displayLanguage
        )
        ExperienceSection(
            breakpoint = breakpoint,
            bigObject = bigObject.value.splitLanguages(displayLanguage.value),
            displayLanguage = displayLanguage
        )
        FooterSection(
            breakpoint = breakpoint,
            context =context,
            displayLanguage = displayLanguage
        )
    }
}


@Composable
fun fetchApiData(bigObject: MutableState<BigObjectUiState>) {
    val scope = rememberCoroutineScope()
    var visitorId by remember { mutableStateOf(localStorage[VISITOR_ID]?:"") }
    scope.launch {
        println("checking visitor data......./")
        if (visitorId.isEmpty()){
            //record new visitor
            println("checking visitor data......./visitorId.isEmpty// adding visitor data")
            localStorage[VISITOR_ID] = requestVisitorAdd(
                Visitor( date = getCurrentDateTime().toString())
            )
        }else{
            val currentVisitor: Visitor = requestVisitorById(visitorId.replace("\"","")).first()
            println("checking visitor data//currentVisitor:$currentVisitor")
            // updating
            val visitorUpdatingResult = requestVisitorUpdate(currentVisitor.copy(date = currentVisitor.date.plus(",${getCurrentDateTime()}")))
            println("checking visitor data//updating completed:$visitorUpdatingResult")
        }

        requestVisitorsCount(
            onSuccess = {
                        bigObject.value = bigObject.value.copy(visitCount = it)
                println("checking visitor data: visitors count  = $it")
            },
            onError = {
                println("checking visitor data:Error counting visitors count /message:${it.message}")
            }
        )


        println("entering the scope //projects")
        requestInfoData(
            onSuccess = { respons: ApiInfoResponse ->
                when (respons) {
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
            onSuccess = { response ->
                when (response) {
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
            onSuccess = { response ->
                when (response) {
                    is ApiProjectResponse.Error -> {
                        println("projects: Error")
                        println(response.message)
                    }

                    ApiProjectResponse.Idle -> println("projects: Response stile Ideal")
                    is ApiProjectResponse.Success -> {
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
            onSuccess = { response ->
                when (response) {
                    is ApiWebsiteResponse.Error -> {
                        println("websites: Error")
                        println(response.message)
                    }

                    ApiWebsiteResponse.Idle -> println("websites: Response stile Ideal")
                    is ApiWebsiteResponse.Success -> {
                        println("websites: success")
//                        println(response.data)
                    }
                }

            },
            onError = {
                println(it.message)
            }
        )


        requestExperienceData(
            onSuccess = { response ->
                when (response) {
                    is ApiExperienceResponse.Error -> {
                        println("experiences: Error")
                        println(response.message)
                    }

                    ApiExperienceResponse.Idle -> println("experiences: Response stile Ideal")
                    is ApiExperienceResponse.Success -> {
                        println("experiences: success99")
                        bigObject.value = bigObject.value.copy(
                            experiencesList = response.data
                        )
                        println(response.data)
                    }
                }
            },
            onError = {
                println(it.message)
            }
        )


        requestWebsitesData(
            onSuccess = { response ->
                when (response) {
                    is ApiWebsiteResponse.Error -> {
                        println("requestWebsitesData.Error:${response.message}")
                    }

                    ApiWebsiteResponse.Idle -> println("requestWebsitesData.Ideal")

                    is ApiWebsiteResponse.Success -> {
                        bigObject.value = bigObject.value.copy(
                            websitesList = response.data
                        )
                    }
                }
            },
            onError = {}
        )


    }

}





