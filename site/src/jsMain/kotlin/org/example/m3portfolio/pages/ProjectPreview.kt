package org.example.m3portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Constants.PROJECT_ID_PARAM
import org.example.m3portfolio.components.MainHeaderPanel
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.sections.FooterSection
import org.example.m3portfolio.sections.project_sections.MainProjectSection
import org.example.m3portfolio.sections.project_sections.ProjectDescriptionSection
import org.example.m3portfolio.util.BigObjectUiState
import org.example.m3portfolio.util.requestProjectDataById
import org.example.m3portfolio.util.splitLanguages
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.w3c.dom.get

@Page(routeOverride = "/project_preview")
@Composable
fun ProjectPreviewPage() {

    val displayLanguage: MutableState<Constants.Languages> = remember(
        window.localStorage[Constants.LANGUAGE_STORAGE_VALUE]
    ) {
        mutableStateOf(
            Constants.Languages.valueOf(
                localStorage[Constants.LANGUAGE_STORAGE_VALUE] ?: "EN"
            )
        )
    }

    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    val colorMode = ColorMode.currentState
    val hasIdParam = remember(context.route) {
        context.route.params.contains(PROJECT_ID_PARAM)
    }
    val projectId = context.route.params[PROJECT_ID_PARAM]

    var selectedProject by remember { mutableStateOf(Project()) }



    LaunchedEffect(key1 = hasIdParam) {
        if (hasIdParam && !projectId.isNullOrEmpty()) {
            requestProjectDataById(projectId).let { response ->
                when (response) {
                    is ApiProjectResponse.Error -> {
                        println("project preview-> response.Error = ${response.message}")
                    }

                    ApiProjectResponse.Idle -> {
                        println("project preview-> response.Ideal")

                    }

                    is ApiProjectResponse.Success -> {
                        println("project preview-> response.Success = ${response.data}")
                        selectedProject = response.data.first()
                    }
                }
            }
        }
    }


    MainHeaderPanel(
        displayLanguage = displayLanguage,
        onLanguageChange = {
            displayLanguage.value = it
        },
        headerNavigationItemsTypes = Constants.HeaderNavigationItemsTypes.ProjectPreviewHeaders
    )
    {
        if (!hasIdParam) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpanText(
                    modifier = Modifier
                        .fontSize(48.px)
                        .fontWeight(FontWeight.Bold)
                        .fontFamily(FONT_FAMILY),
                    text = "this page should be opened with an id parameter"
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                ProjectPreviewContent(
                    context = context,
                    breakpoint = breakpoint,
                    colorMode = colorMode,
                    selectedProject = selectedProject,
                    displayLanguage = displayLanguage
                )

                FooterSection(
                    breakpoint = breakpoint,
                    context = context,
                    displayLanguage = displayLanguage
                )
            }



        }

    }
}

@Composable
fun ProjectPreviewContent(
    context: PageContext,
    selectedProject: Project,
    colorMode: MutableState<ColorMode>,
    breakpoint: Breakpoint,
    displayLanguage: MutableState<Constants.Languages>,
) {
    val bigObject = remember { mutableStateOf(BigObjectUiState()) }
    bigObject.value = bigObject.value.copy(
        projectsList = listOf(selectedProject)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                leftRight = when(breakpoint){
                    Breakpoint.ZERO -> 10.px
                    Breakpoint.SM -> 10.px
                    Breakpoint.MD -> 15.px
                    Breakpoint.LG -> 30.px
                    Breakpoint.XL -> 40.px
                }
                , top = if (breakpoint > Breakpoint.MD)30.px else 20.px

            )
            .backgroundColor(
                if (colorMode.value.isLight) Theme.Item_bk_light.rgb
                else Theme.Them_bk_dark_2.rgb
            )
            .transition(
                Transition.of(
                    property = TransitionProperty.All.toString(),
                    duration = 500.ms
                )
            ),
        verticalArrangement = Arrangement.spacedBy(20.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        MainProjectSection(
            breakpoint = breakpoint,
            bigObject = bigObject.value.splitLanguages(displayLanguage.value),
            context = context,
            displayLanguage = displayLanguage
        )

        ProjectDescriptionSection(
            breakpoint = breakpoint,
            bigObject = bigObject.value.splitLanguages(displayLanguage.value),
            context = context,
            displayLanguage = displayLanguage
        )


    }

}

