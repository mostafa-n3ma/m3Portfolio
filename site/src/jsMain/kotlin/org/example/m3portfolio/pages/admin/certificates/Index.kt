package org.example.m3portfolio.pages.admin.certificates

import androidx.compose.runtime.Composable
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
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.Res
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.Certificate
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.ItemStyle
import org.example.m3portfolio.util.ControllersHeader
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.requestCertificatesData
import org.example.m3portfolio.util.requestDeletingCertificates
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Page
@Composable
fun CertificatesPage() {
    isUserLoggedIn {
        CertificatesScreen()
    }
}

@Composable
fun CertificatesScreen() {
    val breakpoint = rememberBreakpoint()
    val scope: CoroutineScope = rememberCoroutineScope()
    var certificatesList by remember {
        mutableStateOf(listOf<Certificate>())
    }
    val context = rememberPageContext()
    val updateCertificatesList: MutableState<Boolean> = remember { mutableStateOf(true) }


    if (updateCertificatesList.value) {
        scope.launch {
            requestCertificatesData(
                onSuccess = { response ->
                    when (response) {
                        is ApiCertificateResponse.Error -> {
                            println("requesting certificates data response.Error:${response.message}")
                        }

                        ApiCertificateResponse.Idle -> {
                            println("requesting certificates data response.Ideal")
                        }

                        is ApiCertificateResponse.Success -> {
                            certificatesList = response.data
                            updateCertificatesList.value = false
                            println("requesting certificates data response.Success:${response.data}")
                        }
                    }
                },
                onError = {}
            )
        }
    }






    AdminPanelLayout {
        CertificatesScreenContent(
            context,
            certificatesList,
            breakpoint,
            scope = scope,
            onUpdate = {
                updateCertificatesList.value = true
            }
        )
    }


}

@Composable
fun CertificatesScreenContent(
    context: PageContext,
    certificatesList: List<Certificate>,
    breakpoint: Breakpoint,
    scope: CoroutineScope,
    onUpdate: (Boolean) -> Unit
) {
    var selectableMode by remember { mutableStateOf(false) }
    val selectedCertificates = remember { mutableStateListOf<String>() }
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
            addBtnLabel = "Add New Certificate",
            onAddDeleteBtnClicked = {
                if (selectableMode) {
                    // deleting selected certificates
                    scope.launch {
                        val certificatesIsDeleted =
                            requestDeletingCertificates(selectedCertificates)
                        if (certificatesIsDeleted) {
                            messagePopup = "Selected Certificates Deleted Successfully"
                            onUpdate(true)
                            delay(1000)
                            messagePopup = ""
                            selectableMode = false

                        } else {
                            messagePopup = "Error Selected Certificates didn't got Deleted "
                            delay(1000)
                            messagePopup = ""
                        }
                    }
                } else {
                    // navigating to new Certificate page to add new certificate
                    println("clicking add btn")
                    context.router.navigateTo(Screen.AdminCertificatesEdit.route)
                    context.router.navigateTo(Screen.AdminCertificatesEdit.route)
                }
            }
        )


        SimpleGrid(
            numColumns = numColumns(base = 1, md = 2, xl = 3),
            modifier = Modifier.fillMaxWidth()
        ) {
            certificatesList.forEach { certificateItem ->
                Box(
                    modifier = ItemStyle.toModifier()
                        .height(400.px)
                        .width(500.px)
                        .margin(all = 10.px)
                        .borderRadius(
                            topRight = 100.px,
                            topLeft = 5.px,
                            bottomLeft = 5.px,
                            bottomRight = 5.px
                        )
                        .backgroundColor(Theme.Them_bk_light.rgb)
                        .border(
                            width = if (selectableMode) 3.px else 1.px,
                            style = if (selectableMode) {
                                if (selectedCertificates.contains(certificateItem._id)) {
                                    LineStyle.Solid
                                } else {
                                    LineStyle.Dotted
                                }
                            } else {
                                LineStyle.Solid
                            },
                            color = if (selectableMode) {
                                if (selectedCertificates.contains(certificateItem._id)) {
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
                                if (selectedCertificates.contains(certificateItem._id)) {
                                    selectedCertificates.remove(certificateItem._id)
                                } else {
                                    selectedCertificates.add(certificateItem._id)
                                }

                            } else {
                                context.router.navigateTo(
                                    Screen.AdminCertificatesEdit.passCertificateId(
                                        certificateItem._id
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        SpanText(
                            modifier = Modifier
                                .fontFamily(Constants.FONT_FAMILY)
                                .maxWidth(350.px)
                                .fontSize(28.px)
                                .align(Alignment.CenterHorizontally)
                                .fontWeight(FontWeight.Bold)
                                .color(Theme.PrimaryLight.rgb),
                            text = certificateItem.title
                        )

                        SpanText(
                            modifier = Modifier
                                .fontFamily(Constants.FONT_FAMILY)
                                .align(Alignment.Start)
                                .margin(leftRight = 15.percent)
                                .fontSize(20.px)
                                .fontWeight(FontWeight.Normal),
                            text = certificateItem.from
                        )




//
                        Image(
                            modifier = Modifier.width(200.px).height(150.px).align(Alignment.Start).margin(left = 15.percent),
                            src = certificateItem.thumbnailLink
                            )


                        SpanText(
                            modifier = Modifier
                                .margin(left = 15.percent, top = 30.px)
                                .fontFamily(FONT_FAMILY)
                                .fontSize(18.px)
                                .color(Colors.DarkGray)
                            ,
                            text = "See Original Link"
                        )

                    }
                    Image(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(300.px)
                            .zIndex(9)
                        ,
                        src = Res.Image.certificateIcon
                    )

                }
            }

        }





    }


}



















