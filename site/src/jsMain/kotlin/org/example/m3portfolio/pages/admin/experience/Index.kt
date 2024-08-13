package org.example.m3portfolio.pages.admin.experience

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
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.Measurements.SIDE_PANEL_WIDTH
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiExperienceResponse
import org.example.m3portfolio.models.Experience
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.ButtonsStyle
import org.example.m3portfolio.styles.ItemStyle
import org.example.m3portfolio.util.ControllersHeader
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.noBorder
import org.example.m3portfolio.util.requestDeletingExperiences
import org.example.m3portfolio.util.requestDeletingWebsites
import org.example.m3portfolio.util.requestExperienceData
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

@Page
@Composable
fun Index() {
    isUserLoggedIn {
        ExperienceScreen()
    }
}

@Composable
fun ExperienceScreen() {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    var experiences by remember {
        mutableStateOf(listOf<Experience>())
    }

    val updateExperiencesList: MutableState<Boolean> = remember { mutableStateOf(true) }


    val context = rememberPageContext()


    LaunchedEffect(updateExperiencesList.value) {
        scope.launch {
            requestExperienceData(
                onSuccess = { response ->
                    when (response) {
                        is ApiExperienceResponse.Error -> {
                            println(
                                "error${response.message}"
                            )
                        }

                        ApiExperienceResponse.Idle -> println("response is Ideal")
                        is ApiExperienceResponse.Success -> {
                            updateExperiencesList.value = false
                            experiences = response.data
                            println("requesting updates >>>")
                        }
                    }
                },
                onError = {
                    println(
                        it.message
                    )
                }
            )

        }
    }




    AdminPanelLayout {
        ExperiencesScreenContent(
            context = context,
            experiencesList = experiences,
            breakpoint = breakpoint,
            scope = scope,
            onUpdate = {
                updateExperiencesList.value = true
            }
        )


    }

}

@Composable
fun ExperiencesScreenContent(
    context: PageContext,
    experiencesList: List<Experience>,
    breakpoint: Breakpoint,
    scope: CoroutineScope,
    onUpdate: (Boolean) -> Unit
) {


    var selectableMode by remember { mutableStateOf(false) }
    val selectedExperiences = remember { mutableStateListOf<String>() }
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
        ControllersHeader(
            selectableMode = selectableMode,
            selectBtnLabel = "Select",
            onSelectBtnClicked = {
                selectableMode = !selectableMode
            },
            addBtnLabel = "Add New Experience",
            onAddDeleteBtnClicked = {
                if (selectableMode) {
                    // deleting selected experience
                    scope.launch {
                        val experiencesIsDeleted =
                            requestDeletingExperiences(selectedExperiences)
                        if (experiencesIsDeleted) {
                            messagePopup = "Selected Websites Deleted Successfully"
                            onUpdate(true)
                            delay(1000)
                            messagePopup = ""
                            selectableMode = false

                        } else {
                            messagePopup = "Error Selected Websites didn't got Deleted "
                            delay(1000)
                            messagePopup = ""
                        }
                    }
                } else {
                    // navigating to new Certificate page to add new certificate
                    println("clicking add btn")
                    context.router.navigateTo(Screen.AdminExperienceEdit.route)
                }
            }
        )


        SimpleGrid(
            numColumns = numColumns(base = 1, md = 2, xl = 3),
            modifier = Modifier.fillMaxWidth(),
        ) {
            experiencesList.forEach { experienceItem ->
                Column(
                    modifier = Modifier
                        .width(400.px)
                        .height(300.px)
                        .margin(20.px)
                        .padding(leftRight = 8.px)
                        .borderRadius(r = 4.px)
                        .border(
                            width = if (selectableMode) 3.px else 1.px,
                            style = if (selectableMode) {
                                if (selectedExperiences.contains(experienceItem._id)) {
                                    LineStyle.Solid
                                } else {
                                    LineStyle.Dotted
                                }
                            } else {
                                LineStyle.Solid
                            },
                            color = if (selectableMode) {
                                if (selectedExperiences.contains(experienceItem._id)) {
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
                                if (selectedExperiences.contains(experienceItem._id)) {
                                    selectedExperiences.remove(experienceItem._id)
                                } else {
                                    selectedExperiences.add(experienceItem._id)
                                }

                            } else {
                                context.router.navigateTo(
                                    Screen.AdminExperienceEdit.passExpId(
                                        experienceItem._id
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SpanText(
                        modifier = Modifier
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Bold)
                            .fontSize(32.px),
                        text = experienceItem.role,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Bold)
                                .fontSize(24.px),
                            text = "Duration:",
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .fontSize(24.px),
                            text = experienceItem.duration,
                        )
                    }



                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Bold)
                                .fontSize(24.px),
                            text = "Location:",
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .fontSize(24.px),
                            text = experienceItem.location.take(20),
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Bold)
                                .fontSize(24.px),
                            text = "Projects:",
                        )

                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .fontSize(24.px),
                            text = experienceItem.projects.take(30),
                        )
                    }


                }
            }

        }



    }




//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
//        contentAlignment = Alignment.TopCenter
//    ) {
//
//
//
//    }
}


//Column(
//modifier = ButtonsStyle.toModifier()
//.onClick {
//    context.router.navigateTo(Screen.AdminExperienceEdit.route)
//}
//.align(Alignment.BottomEnd)
//.thenIf(
//condition = breakpoint <= Breakpoint.MD,
//other = Modifier.width(100.px).height(54.px)
//)
//.margin(
//bottom = if (breakpoint <= Breakpoint.MD) 70.px else if (breakpoint <= Breakpoint.SM) 10.px else 30.px,
//right = if (breakpoint <= Breakpoint.MD) 70.px else if (breakpoint <= Breakpoint.SM) 10.px else 30.px)
//.position(Position.Fixed)
//.zIndex(9),
//verticalArrangement = Arrangement.Center,
//horizontalAlignment = Alignment.CenterHorizontally
//) {
//    SpanText(
//        modifier = Modifier
//            .fontSize(24.px)
//            .fontFamily(FONT_FAMILY)
//            .fontWeight(FontWeight.Bold)
//            .color(Colors.White)
//            .noBorder(),
//        text = if (breakpoint <= Breakpoint.MD) "+" else "Add New Experience"
//    )
//}