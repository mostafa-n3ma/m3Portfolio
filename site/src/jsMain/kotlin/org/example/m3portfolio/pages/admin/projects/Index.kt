package org.example.m3portfolio.pages.admin.projects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.ItemStyle
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.requestDeletingProjects
import org.example.m3portfolio.util.requestProjectsData
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Page
@Composable
fun Index() {
    isUserLoggedIn {
        ProjectsScreen()
    }
}

@Composable
fun ProjectsScreen() {
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    var projectList: List<Project> by remember { mutableStateOf(mutableListOf()) }
    val updateProjectList: MutableState<Boolean> = remember { mutableStateOf(true) }
    println(updateProjectList.value)
    if (updateProjectList.value) {
        scope.launch {
            println("updating projects list:${updateProjectList.value}")
            requestProjectsData(
                onSuccess = { response ->
                    when (response) {
                        is ApiProjectResponse.Error -> {
                            println("read projects data (onSuccess)->response.Error:${response.message}")
                        }

                        ApiProjectResponse.Idle -> {
                            println("read projects data (onSuccess)->response.Ideal")

                        }

                        is ApiProjectResponse.Success -> {
//                                            println("read projects data (onSuccess)->response.Success:data ${response.data}")
                            projectList = response.data
                            updateProjectList.value = false
                            println("projectList is up to date")
                            println("updateProjectList:${updateProjectList.value}")

                        }
                    }

                },
                onError = {
                    println("read projects data (onError):${it.message}")
                }
            )
        }
    }





    AdminPanelLayout {
        ProjectsContent(context, projectList, breakpoint,
            onUpdate = {
                updateProjectList.value = true
            })
    }
}

@Composable
fun ProjectsContent(
    context: PageContext,
    projectList: List<Project>,
    breakpoint: Breakpoint,
    onUpdate: (Boolean) -> Unit
) {
    var selectableMode by remember { mutableStateOf(false) }
    val selectedProjects = remember { mutableStateListOf<String>() }
    val scope = rememberCoroutineScope()
    var messagePopup by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(left = if (breakpoint > Breakpoint.MD) Measurements.SIDE_PANEL_WIDTH.px else 0.px),
        verticalArrangement = Arrangement.spacedBy(20.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (messagePopup.isNotEmpty()) {
            MessagePopup(
                message = messagePopup,
                onDialogDismiss = {
                    messagePopup = ""
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.px, Alignment.End)
        ) {
            FinalButton(
                onClick = {
                    selectableMode = !selectableMode
                },
                label = "Select",
                color = Theme.Them_bk_light.rgb,
                modifier = ItemStyle
                    .toModifier()
                    .width(200.px),
                textColor = Theme.PrimaryLight.rgb
            )

            FinalButton(
                onClick = {
                    if (selectableMode) {
                        // deleting selected projects
                        scope.launch {
                            val projectsIsDeleted = requestDeletingProjects(selectedProjects)
                            if (projectsIsDeleted) {
                                messagePopup = "Selected Projects Deleted Successfully"
                                onUpdate(true)
                                delay(1000)
                                messagePopup = ""
                                selectableMode = false

                            } else {
                                messagePopup = "Error Selected Projects didn't got Deleted "
                                delay(1000)
                                messagePopup = ""
                            }
                        }
                    } else {
                        // navigating to new project page
                        context.router.navigateTo(Screen.AdminProjectEdit.route)
                    }

                },
                label = when (selectableMode) {
                    true -> "Delete"
                    false -> "Add New Project"
                },
                color = when (selectableMode) {
                    true -> Colors.Red
                    false -> Theme.PrimaryLight.rgb
                },
                modifier = ItemStyle
                    .toModifier()
                    .width(200.px),
            )


        }
        SimpleGrid(
            numColumns = numColumns(base = 1, md = 2, xl = 3),
            modifier = Modifier.fillMaxWidth(),
        ) {
            projectList.forEach { projectItem ->
                Column(
                    modifier = ItemStyle.toModifier()
                        .width(95.percent)
                        .height(500.px)
                        .margin(all = 10.px)
                        .borderRadius(r = 4.px)
                        .border(
                            width = if (selectableMode) 5.px else 2.px,
                            style = if (selectableMode) {
                                if (selectedProjects.contains(projectItem._id)) {
                                    LineStyle.Solid
                                } else {
                                    LineStyle.Dotted
                                }
                            } else {
                                LineStyle.Solid
                            },
                            color = if (selectableMode) {
                                if (selectedProjects.contains(projectItem._id)) {
                                    Colors.Red
                                } else {
                                    Theme.PrimaryLight.rgb
                                }
                            } else {
                                Theme.PrimaryLight.rgb

                            }

                        )
                        .backgroundColor(Theme.Them_bk_light.rgb)
                        .onClick {
                            if (selectableMode) {
                                if (selectedProjects.contains(projectItem._id)) {
                                    selectedProjects.remove(projectItem._id)
                                } else {
                                    selectedProjects.add(projectItem._id)
                                }

                            } else {
                                context.router.navigateTo(
                                    Screen.AdminProjectEdit.passProjectId(
                                        projectItem._id
                                    )
                                )
                            }

                        }
                        .thenIf(
                            condition = selectableMode,
                            other = Modifier
                                .scale(
                                if (selectableMode) 95.percent
                                else 100.percent
                            )
                        )
                        ,
                    verticalArrangement = Arrangement.spacedBy(10.px),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(300.px),
                        src = "${projectItem.mainImageLink}/"
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(left = 8.px)
                    ) {

                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Bold),
                            text = projectItem.title
                        )
                        SpanText(
                            text = projectItem.date
                        )

                        SpanText(
                            text = projectItem.description.take(200)
                        )
                    }

                }
            }
        }
    }
}


