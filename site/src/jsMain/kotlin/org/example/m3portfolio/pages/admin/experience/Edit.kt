package org.example.m3portfolio.pages.admin.experience

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
import org.example.m3portfolio.Constants.EXPERIENCE_ID_PARAM
import org.example.m3portfolio.Ids.exp_description_Editor
import org.example.m3portfolio.Ids.exp_description_Preview
import org.example.m3portfolio.Ids.exp_duration_Field
import org.example.m3portfolio.Ids.exp_image_Field
import org.example.m3portfolio.Ids.exp_location_Field
import org.example.m3portfolio.Ids.exp_projects_Field
import org.example.m3portfolio.Ids.exp_role_Field
import org.example.m3portfolio.Measurements.SIDE_PANEL_WIDTH
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.ControlPopup
import org.example.m3portfolio.components.EditorComponent
import org.example.m3portfolio.components.EditorControllersPanel
import org.example.m3portfolio.components.FieldComponent
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiExperienceResponse
import org.example.m3portfolio.models.ControlStyle
import org.example.m3portfolio.models.EditorControlIcons
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.util.applyControlStyle
import org.example.m3portfolio.util.applyStyle
import org.example.m3portfolio.util.applyToPreview
import org.example.m3portfolio.util.calculateExperiencePageValues
import org.example.m3portfolio.util.getEditor
import org.example.m3portfolio.util.getSelectedText
import org.example.m3portfolio.util.requestExperienceAdd
import org.example.m3portfolio.util.requestExperienceDataById
import org.example.m3portfolio.util.requestExperienceDataUpdate
import org.jetbrains.compose.web.css.px


data class ExperienceUiState(
    var id: String = "",
    val description: String = "",
    val duration: String = "",
    val image: String = "",
    val location: String = "",
    val projects: String = "",
    val role: String = "",
    val editorVisibility: Boolean = true,
    var messagePopup: String = "",
    var linkPopup: Boolean = false,
    var imagePopup: Boolean = false
)


@Page
@Composable
fun EditPage() {
    val context = rememberPageContext()
    val hasIdParam: Boolean = remember(key1 = context.route) {
        context.route.params.contains(EXPERIENCE_ID_PARAM)
    }

    AdminPanelLayout {
        ExperienceEditScreen(hasIdParam)

    }


}

@Composable
fun ExperienceEditScreen(hasIdParam: Boolean) {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val experience_id: String? = context.route.params[EXPERIENCE_ID_PARAM]
    var uiState by remember { mutableStateOf(ExperienceUiState()) }


    LaunchedEffect(hasIdParam) {
        scope.launch {
            if (!experience_id.isNullOrEmpty()) {
                val result: ApiExperienceResponse = requestExperienceDataById(id = experience_id)
                when (result) {
                    is ApiExperienceResponse.Error -> println("from luanchedEffect Edit page response.Error : ${result.message}")
                    ApiExperienceResponse.Idle -> println("from luanchedEffect Edit page response.Ideal")
                    is ApiExperienceResponse.Success -> {
                        uiState = uiState.copy(
                            id = result.data.first()._id,
                            description = result.data.first().description,
                            duration = result.data.first().duration,
                            image = result.data.first().image,
                            location = result.data.first().location,
                            projects = result.data.first().projects,
                            role = result.data.first().role
                        )
                        getEditor(exp_description_Editor).value = uiState.description
                        applyToPreview(preview_id = exp_description_Preview, editor_id = exp_description_Editor)

                    }
                }

            }


        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(Theme.Them_bk_light.rgb)
            .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
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
                text = if (hasIdParam) "ID:$experience_id" else "",
                modifier = Modifier
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(32.px)
                    .color(Theme.HalfBlack.rgb)
            )

                FieldComponent(
                    tag = "Role",
                    inputField_id = exp_role_Field,
                    placeHolder = "add role here",
                    value = uiState.role
                )
                FieldComponent(
                    tag = "Duration",
                    inputField_id = exp_duration_Field,
                    placeHolder = "add duration here",
                    value = uiState.duration
                )

                FieldComponent(
                    tag = "Location",
                    inputField_id = exp_location_Field,
                    placeHolder = "add location here",
                    value = uiState.location
                )


                FieldComponent(
                    tag = "Image Url:",
                    inputField_id = exp_image_Field,
                    placeHolder = "add location here",
                    value = uiState.image
                )
                FieldComponent(
                    tag = "Projects:",
                    inputField_id = exp_projects_Field,
                    placeHolder = "add location here",
                    value = uiState.projects
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
                    editor_id = exp_description_Editor,
                    preview_id = exp_description_Preview
                )

                EditorComponent(
                    editor_id = exp_description_Editor,
                    preview_id = exp_description_Preview,
                    breakpoint = breakpoint,
                    editorVisibility = uiState.editorVisibility
                )

                FinalButton(
                    onClick = {
                        scope.launch {
                            if (hasIdParam){
                                //updating
                                if (requestExperienceDataUpdate(calculateExperiencePageValues().copy(_id = uiState.id))){
                                    uiState = uiState.copy(messagePopup = "experience data updated")
                                    delay(3000)
                                    uiState = uiState.copy(messagePopup = "")
                                }
                            }else{
                                //adding
                                if (requestExperienceAdd(calculateExperiencePageValues())){
                                    uiState = uiState.copy(messagePopup = "experience data Added")
                                    delay(3000)
                                    uiState = uiState.copy(messagePopup = "")
                                    context.router.navigateTo(Screen.AdminExperience.route)
                                }
                            }
                        }

                    },
                    label = if (hasIdParam) "Update" else "Add",
                    color = if (hasIdParam) Colors.Orange else Theme.PrimaryLight.rgb
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

        if (uiState.linkPopup) {
            ControlPopup(
                editorControl = EditorControlIcons.Link,
                onDialogDismiss = { uiState = uiState.copy(linkPopup = false) },
                onAddClick = { href, title ->
                    applyControlStyle(
                        editorControl = EditorControlIcons.Link,
                        onImageViewClicked = {},
                        editor_id = exp_description_Editor,
                        preview_id = exp_description_Preview,
                        onLinkViewClicked = {
                            applyStyle(
                                ControlStyle.Link(
                                    href = href,
                                    title = title,
                                    selectedText = getSelectedText(editor_id = exp_description_Editor)
                                ),
                                editor_id = exp_description_Editor,
                                preview_id = exp_description_Preview,
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
                                    selectedText = getSelectedText(editor_id = exp_description_Editor),
                                    imageUrl = url,
                                    alt = desription
                                ),
                                editor_id = exp_description_Editor,
                                preview_id = exp_description_Preview,
                            )
                        },
                        onLinkViewClicked = {},
                        editor_id = exp_description_Editor,
                        preview_id = exp_description_Preview,
                    )
                }
            )
        }




    }


}
