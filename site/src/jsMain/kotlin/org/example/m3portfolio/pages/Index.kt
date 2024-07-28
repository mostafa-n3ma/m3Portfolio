package org.example.m3portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.Res
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
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.models.Website
import org.example.m3portfolio.sections.MainSection
import org.example.m3portfolio.sections.ProjectsSection
import org.example.m3portfolio.styles.GithubBtnStyle
import org.example.m3portfolio.util.noBorder
import org.example.m3portfolio.util.requestCertificatesData
import org.example.m3portfolio.util.requestInfoData
import org.example.m3portfolio.util.requestProjectsData
import org.example.m3portfolio.util.requestWebsitesData
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div


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





