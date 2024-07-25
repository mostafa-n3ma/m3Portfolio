package org.example.m3portfolio.pages.admin.websites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Ids
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.FieldComponent
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiWebsiteResponse
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.util.calculatingWebsitePageValues
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.requestWebsiteById
import org.example.m3portfolio.util.requestWebsiteDataAdd
import org.example.m3portfolio.util.requestWebsiteDataUpdate
import org.jetbrains.compose.web.css.px


data class WebsiteUiState(
    var _id:String="",
    var title: String="",
    var link: String="",
    var icon: String = "",
    var messagePopup: String = "",
)



@Page
@Composable
fun EditPage() {
    isUserLoggedIn {
        WebsitesEditScreen()
    }
}

@Composable
fun WebsitesEditScreen() {
    val context = rememberPageContext()
    val hasIdPAram = remember(key1 = context.route) {
        context.route.params.contains(Constants.WEBSITE_ID_PARAM)
    }


    AdminPanelLayout {
        WebsiteEditScreenContent(hasIdPAram)
    }
}

@Composable
fun WebsiteEditScreenContent(hasIdPAram: Boolean) {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val website_id: String? = context.route.params[Constants.WEBSITE_ID_PARAM]
    var uiState by remember { mutableStateOf(WebsiteUiState()) }



    LaunchedEffect(hasIdPAram){
        scope.launch {
            if (!website_id.isNullOrEmpty()){
                val result: ApiWebsiteResponse = requestWebsiteById(website_id)
                when(result){
                    is ApiWebsiteResponse.Error -> {
                        println("requestWebsiteById:response.Error:message=${result.message}")
                    }
                    ApiWebsiteResponse.Idle -> {
                        println("requestWebsiteById:response.Ideal")
                    }
                    is ApiWebsiteResponse.Success -> {
                        println("requestWebsiteById:response.Success.data = ${result.data.first()}")
                        uiState = uiState.copy(
                            _id = result.data.first()._id,
                            title = result.data.first().title,
                            link = result.data.first().link,
                            icon = result.data.first().icon
                        )
                    }
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

        if (uiState.messagePopup.isNotEmpty()) {
            MessagePopup(
                message = uiState.messagePopup,
                onDialogDismiss = {
                    uiState = uiState.copy(messagePopup = "")
                }
            )
        }


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
                text = if (hasIdPAram) "ID:$website_id" else "",
                modifier = Modifier
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(32.px)
                    .color(Theme.HalfBlack.rgb)
            )

            FieldComponent(
                tag = "Title",
                inputField_id = Ids.website_title_field,
                placeHolder = "add role here",
                value = uiState.title
            )
            FieldComponent(
                tag = "link",
                inputField_id = Ids.website_link_field,
                placeHolder = "add role here",
                value = uiState.link
            )
            FieldComponent(
                tag = "Icon url",
                inputField_id = Ids.website_icon_field,
                placeHolder = "add role here",
                value = uiState.icon
            )


            FinalButton(
                onClick = {
                    scope.launch {
                        if (hasIdPAram) {
                            // updating
                            val updatedWebsite = calculatingWebsitePageValues().copy(_id = website_id!!)
                            if (requestWebsiteDataUpdate(updatedWebsite)){
                                uiState = uiState.copy(messagePopup = "Website data updated")
                                delay(3000)
                                uiState = uiState.copy(messagePopup = "")
                            }
                        }else{
                            //Adding
                            val newWebsite = calculatingWebsitePageValues()
                            if (requestWebsiteDataAdd(newWebsite)){
                                uiState = uiState.copy(messagePopup = "Website data Added")
                                delay(3000)
                                uiState = uiState.copy(messagePopup = "")
                                context.router.navigateTo(Screen.AdminWebsites.route)
                            }
                        }
                    }
                },
                label = if (hasIdPAram) "Update" else "Add",
                color = if (hasIdPAram) Colors.Orange else Theme.PrimaryLight.rgb,
            )










        }
    }









}

























