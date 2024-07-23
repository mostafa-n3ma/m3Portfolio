package org.example.m3portfolio.pages.admin

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
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids.info_AddressField
import org.example.m3portfolio.Ids.info_Bio_editor
import org.example.m3portfolio.Ids.info_Bio_preview
import org.example.m3portfolio.Ids.info_EducationField
import org.example.m3portfolio.Ids.info_EmailField
import org.example.m3portfolio.Ids.info_ExtraField
import org.example.m3portfolio.Ids.info_GithubField
import org.example.m3portfolio.Ids.info_ImageUrlField
import org.example.m3portfolio.Ids.info_LinkedInField
import org.example.m3portfolio.Ids.info_NameField
import org.example.m3portfolio.Ids.info_PhoneField
import org.example.m3portfolio.Ids.info_ResumeLinkField
import org.example.m3portfolio.Ids.info_RoleField
import org.example.m3portfolio.Ids.info_SkillsField
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.ControlPopup
import org.example.m3portfolio.components.EditorControllersPanel
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.components.EditorComponent
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.ControlStyle
import org.example.m3portfolio.models.EditorControlIcons
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.util.applyControlStyle
import org.example.m3portfolio.util.applyStyle
import org.example.m3portfolio.util.applyToPreview
import org.example.m3portfolio.util.calculateInfoPageValues
import org.example.m3portfolio.util.getEditor
import org.example.m3portfolio.util.getSelectedText
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.noBorder
import org.example.m3portfolio.util.requestInfoData
import org.example.m3portfolio.util.requestInfoDataUpdate
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input

@Page
@Composable
fun HomePage() {
    isUserLoggedIn {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    AdminPanelLayout {
        InfoScreenContent()
    }
}

data class InfoScreenUiState(
    var id: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var role: String = "",
    var address: String = "",
    var phone: String = "",
    var email: String = "",
    var linkedIn: String = "",
    var github: String = "",
    var bio: String = "",
    var education: String = "",
    var skills: String = "",
    var resumeLink: String = "",
    var extra: String = "",
    var editorVisibility: Boolean = true,
    var messagePopup: String = "",
    var linkPopup: Boolean = false,
    var imagePopup: Boolean = false
)

@Composable
fun InfoScreenContent() {
    val breakpoint = rememberBreakpoint()
    var uiState by remember { mutableStateOf(InfoScreenUiState()) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        requestInfoData(
            onSuccess = { apiResponse ->
                when (apiResponse) {
                    is ApiInfoResponse.Error -> {
//                           show popup
                    }

                    ApiInfoResponse.Idle -> {
                        //     show popup
                    }

                    is ApiInfoResponse.Success -> {
                        uiState = uiState.copy(
                            id = apiResponse.data._id,
                            name = apiResponse.data.name,
                            imageUrl = apiResponse.data.imageUrl,
                            role = apiResponse.data.role,
                            address = apiResponse.data.address,
                            phone = apiResponse.data.phone,
                            email = apiResponse.data.email,
                            linkedIn = apiResponse.data.linkedIn,
                            github = apiResponse.data.github,
                            bio = apiResponse.data.bio,
                            education = apiResponse.data.education,
                            skills = apiResponse.data.skills,
                            resumeLink = apiResponse.data.resumeLink,
                            extra = apiResponse.data.extra
                        )
                        getEditor(id = info_Bio_editor).value = uiState.bio
                        applyToPreview(preview_id = info_Bio_preview, editor_id = info_Bio_editor)
                    }
                }
            },
            onError = {
//                    show popup

            }
        )

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(Theme.Them_bk_light.rgb)
            .padding(left = if (breakpoint > Breakpoint.MD) 250.px else 0.px),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.px)
                .margin(topBottom = 50.px)
                .maxWidth(700.px),
            verticalArrangement = Arrangement.spacedBy(30.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //name
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Name: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_NameField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "name here")
                            attr("value", uiState.name)
                        }
                )

            }

            //image url
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Image Url: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_ImageUrlField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "image url here")
                            attr("value", uiState.imageUrl)
                        }
                )

            }

            //role
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Role: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_RoleField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "Role here")
                            attr("value", uiState.role)
                        }
                )

            }

            //address
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Address: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_AddressField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "Address here")
                            attr("value", uiState.address)
                        }
                )

            }


            //phone
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Phone: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_PhoneField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "phone here")
                            attr("value", uiState.phone)
                        }
                )

            }


            //email
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "email: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_EmailField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "email here")
                            attr("value", uiState.email)
                        }
                )

            }


            //linkedIn
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "LinkedIn: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_LinkedInField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "LinkedIn link here")
                            attr("value", uiState.linkedIn)
                        }
                )

            }


            //github
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Github: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_GithubField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "Github link here")
                            attr("value", uiState.github)
                        }
                )

            }



            //education
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Education: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_EducationField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "Education here")
                            attr("value", uiState.education)
                        }
                )

            }


            //skills
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Skills (split by ',') : ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_SkillsField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "Skills here")
                            attr("value", uiState.skills)
                        }
                )

            }


            //resumeLink
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Resume link: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_ResumeLinkField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "Resume here")
                            attr("value", uiState.resumeLink)
                        }
                )

            }


            //bio
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Bio: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                EditorControllersPanel(
                    breakpoint = breakpoint,
                    editorVisibility = uiState.editorVisibility,
                    onEditorVisibilityChanged = {
                        uiState = uiState.copy(editorVisibility = !uiState.editorVisibility)
                    },
                    onLinkViewClicked = {
                        uiState = uiState.copy(linkPopup = true)
                    },
                    onImageViewClicked = {
                        uiState = uiState.copy(imagePopup = true)
                    },
                    editor_id = info_Bio_editor,
                    preview_id = info_Bio_preview
                )

                EditorComponent(
                    editor_id = info_Bio_editor,
                    preview_id = info_Bio_preview,
                    breakpoint = breakpoint,
                    editorVisibility = uiState.editorVisibility
                )


            }

            //extra
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpanText(
                    text = "Extra Content: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(32.px)
                        .fontWeight(FontWeight.Bold)
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(info_ExtraField)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .noBorder()
                        .borderRadius(r = 5.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .backgroundColor(Theme.Item_bk_light.rgb)
                        .toAttrs {
                            attr("placeholder", "Extra Content here")
                            attr("value", uiState.extra)
                        }
                )

            }


            FinalButton(
                onClick = {
                    scope.launch {
                        if (requestInfoDataUpdate(calculateInfoPageValues().copy(_id = uiState.id))) {
                            uiState = uiState.copy(messagePopup = "info data updated")
                            delay(3000)
                            uiState = uiState.copy(messagePopup = "")
                        }
                    }
                },
                label = "Update",
                color = Colors.Orange
            )
        }

    }
    if (uiState.messagePopup.isNotEmpty()) {
        MessagePopup(
            message = uiState.messagePopup,
            onDialogDismiss = {
                uiState = uiState.copy(messagePopup = "")
            }
        )
    }
    if (uiState.linkPopup) {
        ControlPopup(
            editorControl = EditorControlIcons.Link,
            onDialogDismiss = { uiState = uiState.copy(linkPopup = false) },
            onAddClick = { href, title ->
                applyControlStyle(
                    editorControl = EditorControlIcons.Link,
                    onImageViewClicked = {},
                    editor_id = info_Bio_editor,
                    preview_id = info_Bio_preview,
                    onLinkViewClicked = {
                        applyStyle(
                            ControlStyle.Link(
                                href = href,
                                title = title,
                                selectedText = getSelectedText(editor_id = info_Bio_editor)
                            ),
                            editor_id = info_Bio_editor,
                            preview_id = info_Bio_preview,
                        )
                    }
                )
            }
        )
    }



    if (uiState.imagePopup) {
        ControlPopup(
            editorControl = EditorControlIcons.Image,
            onDialogDismiss = { uiState = uiState.copy(imagePopup = false) },
            onAddClick = { url, desription ->
                applyControlStyle(
                    editorControl = EditorControlIcons.Image,
                    onImageViewClicked = {
                        applyStyle(
                            ControlStyle.Image(
                                selectedText = getSelectedText(editor_id = info_Bio_editor),
                                imageUrl = url,
                                alt = desription
                            ),
                            editor_id = info_Bio_editor,
                            preview_id = info_Bio_preview,
                        )
                    },
                    onLinkViewClicked = {},
                    editor_id = info_Bio_editor,
                    preview_id = info_Bio_preview,
                )
            }
        )
    }

}
