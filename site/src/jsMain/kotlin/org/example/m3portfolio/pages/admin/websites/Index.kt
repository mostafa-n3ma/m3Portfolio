package org.example.m3portfolio.pages.admin.websites

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
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.rotate
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
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.models.Website
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.util.ControllersHeader
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.requestDeletingCertificates
import org.example.m3portfolio.util.requestDeletingWebsites
import org.example.m3portfolio.util.requestWebsiteDataAdd
import org.example.m3portfolio.util.requestWebsitesData
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Page
@Composable
fun WebsitesPage() {
    isUserLoggedIn {
        WebsitesScreen()
    }
}

@Composable
fun WebsitesScreen() {
    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    var websitesList by remember {
        mutableStateOf(listOf<Website>())
    }
    val updateWebsitesList: MutableState<Boolean> = remember { mutableStateOf(true) }


    LaunchedEffect(updateWebsitesList.value) {
        scope.launch {
            requestWebsitesData(
                onSuccess = { response ->
                    when (response) {
                        is ApiWebsiteResponse.Error -> {
                            println("requestWebsitesData-> response.Error:${response.message}")
                        }

                        ApiWebsiteResponse.Idle -> {
                            println("requestWebsitesData-> response.Ideal")

                        }

                        is ApiWebsiteResponse.Success -> {
                            println("requestWebsitesData-> response.Success:data:${response.data}")
                            updateWebsitesList.value = false
                            websitesList = response.data
                        }
                    }

                },
                onError = {

                }
            )
        }
    }


    AdminPanelLayout {
        WebsiteScreenContent(
            context = context,
            websitesList = websitesList,
            breakpoint = breakpoint,
            scope = scope,
            onUpdate = {
                updateWebsitesList.value = true
            }
        )
    }


}

@Composable
fun WebsiteScreenContent(
    context: PageContext,
    websitesList: List<Website>,
    breakpoint: Breakpoint,
    scope: CoroutineScope,
    onUpdate: (Boolean) -> Unit
) {

    var selectableMode by remember { mutableStateOf(false) }
    val selectedWebsites = remember { mutableStateListOf<String>() }
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
            addBtnLabel = "Add New Website",
            onAddDeleteBtnClicked = {
                if (selectableMode) {
                    // deleting selected certificates
                    println("clicking delete btn")

                    scope.launch {
                        val websitesIsDeleted =
                            requestDeletingWebsites(selectedWebsites)
                        if (websitesIsDeleted) {
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
                    context.router.navigateTo(Screen.AdminWebsiteEdit.route)
//                    context.router.navigateTo(Screen.AdminCertificatesEdit.route)
                }
            }
        )


        SimpleGrid(
            numColumns = numColumns(base = 1, md = 2, lg = 3, xl = 4),
            modifier = Modifier.fillMaxWidth(),
        ) {

            websitesList.forEach { websiteItem ->
                Box(
                    modifier = Modifier
                        .height(150.px)
                        .width(300.px)
                        .margin(all = 5.px)
                        .borderRadius(
                            r = 4.px
                        )
                        .backgroundColor(Theme.Them_bk_light.rgb)
                        .border(
                            width = if (selectableMode) 3.px else 1.px,
                            style = if (selectableMode) {
                                if (selectedWebsites.contains(websiteItem._id)) {
                                    LineStyle.Solid
                                } else {
                                    LineStyle.Dotted
                                }
                            } else {
                                LineStyle.Solid
                            },
                            color = if (selectableMode) {
                                if (selectedWebsites.contains(websiteItem._id)) {
                                    Colors.Red
                                } else {
                                    Theme.PrimaryLight.rgb
                                }
                            } else {
                                Theme.PrimaryLight.rgb

                            }
                        )
                        .onClick {
                            if (selectableMode) {
                                if (selectedWebsites.contains(websiteItem._id)) {
                                    selectedWebsites.remove(websiteItem._id)
                                } else {
                                    selectedWebsites.add(websiteItem._id)
                                }

                            } else {
                                context.router.navigateTo(
                                    Screen.AdminWebsiteEdit.passWebsiteId(
                                        websiteItem._id
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
                ) {

                    SpanText(
                        modifier = Modifier
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontWeight(FontWeight.Bold)
                            .fontSize(32.px)
                            .align(Alignment.TopStart)
                            .margin(20.px)
                            .color(Theme.PrimaryLight.rgb),
                        text = websiteItem.title
                    )
                    SpanText(
                        modifier = Modifier
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontWeight(FontWeight.Normal)
                            .fontSize(10.px)
                            .margin(bottom = 50.px, left = 10.px)
                            .align(Alignment.BottomStart)
                            .color(Theme.HalfBlack.rgb),
                        text = websiteItem.link.take(30)
                    )


                    Image(
                        modifier = Modifier
                            .size(128.px)
                            .align(Alignment.CenterEnd).margin(10.px)
                            .borderRadius(r = 4.px)
                        ,
                        src = websiteItem.icon
                    )


                }


            }


        }


    }


}
