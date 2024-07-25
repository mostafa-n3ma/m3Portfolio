package org.example.m3portfolio.pages.admin.certificates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.FieldComponent
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiCertificateResponse
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.ItemStyle
import org.example.m3portfolio.util.ThumbnailUpLoader
import org.example.m3portfolio.util.calculatingCertificatePageValues
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.requestCertificateById
import org.example.m3portfolio.util.requestCertificateDataAdd
import org.example.m3portfolio.util.requestCertificateDataUpdate
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLInputElement


data class CertificateUiState(
    val _id: String = "",
    val title: String = "",
    val from: String = "",
    val link: String = "",
    val date: String = "",
    var thumbnailLink: String = "",
    var messagePopup: String = "",
    var thumbnailInputDisabled: Boolean = true

)


@Page
@Composable
fun EditPAge() {
    val context = rememberPageContext()
    val hasIdPAram = remember(key1 = context.route) {
        context.route.params.contains(Constants.CERTIFICATE_ID_PARAM)
    }


    isUserLoggedIn {
        AdminPanelLayout {
            CertificateEditScreen(hasIdPAram)
        }
    }



}

@Composable
fun CertificateEditScreen(hasIdPAram: Boolean) {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val certificate_id: String? = context.route.params[Constants.CERTIFICATE_ID_PARAM]
    var uiState by remember { mutableStateOf(CertificateUiState()) }


    LaunchedEffect(hasIdPAram) {
        if (!certificate_id.isNullOrEmpty()) {
            val result: ApiCertificateResponse = requestCertificateById(certificate_id)
            when (result) {
                is ApiCertificateResponse.Error -> {
                    println("requestCertificateById:id($certificate_id)= response.Error.message=${result.message}")
                }

                ApiCertificateResponse.Idle -> {
                    println("requestCertificateById:id($certificate_id)= response.Ideal")

                }

                is ApiCertificateResponse.Success -> {
                    println("requestCertificateById:id($certificate_id)= response.success=${result.data}")
                    uiState = uiState.copy(
                        _id = result.data.first()._id,
                        title = result.data.first().title,
                        from = result.data.first().from,
                        link = result.data.first().link,
                        date = result.data.first().date,
                        thumbnailLink = result.data.first().thumbnailLink
                    )
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(Theme.Them_bk_light.rgb)
            .padding(left = if (breakpoint > Breakpoint.MD) Measurements.SIDE_PANEL_WIDTH.px else 0.px),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.px)
                .margin(top = 50.px)
                .maxWidth(700.px),
            verticalArrangement = Arrangement.spacedBy(30.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpanText(
                text = if (hasIdPAram) "ID:$certificate_id" else "",
                modifier = Modifier
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(32.px)
                    .color(Theme.HalfBlack.rgb)
            )


            FieldComponent(
                tag = "Title",
                inputField_id = Ids.certificate_title_field,
                placeHolder = "add title here",
                value = uiState.title
            )

            FieldComponent(
                tag = "From",
                inputField_id = Ids.certificate_from_field,
                placeHolder = "add from where here",
                value = uiState.from
            )
            FieldComponent(
                tag = "Original Link",
                inputField_id = Ids.certificate_link_field,
                placeHolder = "add link here",
                value = uiState.link
            )
            FieldComponent(
                tag = "Date",
                inputField_id = Ids.certificate_date_field,
                placeHolder = "add Date here",
                value = uiState.date
            )

                SpanText(
                    text = "Current Thumbnail:",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(Constants.FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Image(
                    modifier = Modifier
                        .size(300.px),
                    src = uiState.thumbnailLink
                )


            ThumbnailUpLoader(
                field_id = Ids.certificate_thumbnailInput_field,
                thumbnail = "",
                thumbnailInputDisabled = uiState.thumbnailInputDisabled,
                onThumbnailSelect = { fileName, file ->
                    (document.getElementById(Ids.certificate_thumbnailInput_field) as HTMLInputElement).value =
                        fileName
                    // save the file in the thumbnail
                    uiState = uiState.copy(thumbnailLink = file)

                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .margin(bottom = 12.px),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Switch(
                    modifier = Modifier.margin(right = 8.px),
                    checked = !uiState.thumbnailInputDisabled,
                    onCheckedChange = {
                        uiState = uiState.copy(thumbnailInputDisabled = !it)
                    },
                    size = SwitchSize.MD
                )
                SpanText(
                    modifier = Modifier
                        .fontSize(14.px)
                        .fontFamily(FONT_FAMILY)
                        .color(Theme.HalfBlack.rgb),
                    text = "Paste an Image URL instead"
                )
            }


            FinalButton(
                label = if (hasIdPAram) "Update" else "Add",
                color = if (hasIdPAram) Colors.Orange else Theme.PrimaryLight.rgb,
                onClick = {
                    if (hasIdPAram) {
                        // updating certificate
                        val updatedCertificate = calculatingCertificatePageValues().copy(thumbnailLink = uiState.thumbnailLink, _id = uiState._id)
                        println("clicking the final btn hasIdParam = $hasIdPAram // update updatedCertificate:$updatedCertificate")

                        scope.launch {
                            val result: Boolean =requestCertificateDataUpdate(updatedCertificate)
                            if (result){
                                uiState = uiState.copy(messagePopup = "Certificate Date updated Successfully")
                                delay(1000)
                                uiState = uiState.copy(messagePopup = "")
                                context.router.navigateTo(Screen.AdminCertificates.route)
                            }else{
                                uiState = uiState.copy(messagePopup = "Certificate Date are not updated Dou To Some Error")
                                delay(1000)
                                uiState = uiState.copy(messagePopup = "")
                            }
                        }
                    } else {
                        // add new certificate
                        val newCertificate = calculatingCertificatePageValues().copy(thumbnailLink = uiState.thumbnailLink)
                        println("clicking the final btn hasIdParam = $hasIdPAram // adding newCertificate:$newCertificate")
                        scope.launch {
                            val result = requestCertificateDataAdd(
                                newCertificate
                            )
                            if (result){
                                uiState = uiState.copy(messagePopup = "Certificate Date Added Successfully")
                                delay(1000)
                                uiState = uiState.copy(messagePopup = "")
                                context.router.navigateTo(Screen.AdminCertificates.route)
                            }else{
                                uiState = uiState.copy(messagePopup = "Certificate Date are not Added Dou To Some Error")
                                delay(1000)
                                uiState = uiState.copy(messagePopup = "")
                            }

                        }
                    }
                }
            )


        }

        if (uiState.messagePopup.isNotEmpty()) {
            MessagePopup(
                message = uiState.messagePopup,
                onDialogDismiss = {
                    uiState = uiState.copy(messagePopup = "")
                }
            )
        }

    }


}
