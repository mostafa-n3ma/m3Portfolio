package org.example.m3portfolio.pages.admin.projects

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
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.core.Page
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
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.PROJECT_ID_PARAM
import org.example.m3portfolio.Ids
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.ControlPopup
import org.example.m3portfolio.components.EditorComponent
import org.example.m3portfolio.components.EditorControllersPanel
import org.example.m3portfolio.components.FieldComponent
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.components.ImageUrlPopup
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.ApiProjectResponse
import org.example.m3portfolio.models.ControlStyle
import org.example.m3portfolio.models.EditorControlIcons
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.ItemStyle
import org.example.m3portfolio.util.applyControlStyle
import org.example.m3portfolio.util.applyStyle
import org.example.m3portfolio.util.applyToPreview
import org.example.m3portfolio.util.calculatingProjectPageValues
import org.example.m3portfolio.util.getEditor
import org.example.m3portfolio.util.getSelectedText
import org.example.m3portfolio.util.requestProjectDataAdd
import org.example.m3portfolio.util.requestProjectDataById
import org.example.m3portfolio.util.requestProjectDataUpdate
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px


data class ProjectUiState(
    val _id: String = "",
    val title: String = "",
    val description: String = "",
    val techStack: String = "",
    val repoLink: String = "",
    val videoLink: String = "",
    val mainImageLink: String = "",
    val imagesList: List<String> = emptyList<String>(),
    val date: String = "",
    val editorVisibility: Boolean = true,
    var messagePopup: String = "",
    var linkPopup: Boolean = false,
    var imagePopup: Boolean = false,
    var addToImageListPopup:String = ""
)


@Page
@Composable
fun EditPage() {
    val context = rememberPageContext()
    val hasIdPAram = remember(key1 = context.route) {
        context.route.params.contains(PROJECT_ID_PARAM)
    }

    AdminPanelLayout {
        ProjectEditScreen(hasIdPAram)
    }


}

@Composable
fun ProjectEditScreen(hasIdPAram: Boolean) {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val project_id: String? = context.route.params[PROJECT_ID_PARAM]
    var uiState by remember { mutableStateOf(ProjectUiState()) }


    LaunchedEffect(hasIdPAram){
        if (!project_id.isNullOrEmpty()){
            val result = requestProjectDataById(project_id)
            when(result){
                is ApiProjectResponse.Error -> {
                    println(
                        "requestProjectDataById($project_id):response.Error>>${result.message}"
                    )
                }
                ApiProjectResponse.Idle -> {
                    println(
                        "requestProjectDataById($project_id):response.Ideal"
                    )
                }
                is ApiProjectResponse.Success -> {
                    println(
                        "requestProjectDataById($project_id):response.Success>>${result.data}"
                    )
                    uiState = uiState.copy(
                        _id = result.data.first()._id,
                        title = result.data.first().title,
                        description = result.data.first().description,
                        techStack = result.data.first().techStack,
                        repoLink = result.data.first().repoLink,
                        videoLink = result.data.first().videoLink,
                        mainImageLink = result.data.first().mainImageLink,
                        imagesList = result.data.first().imagesList,
                        date =result.data.first().date
                    )
                    getEditor(Ids.project_description_editor).value = uiState.description
                    applyToPreview(preview_id = Ids.project_description_preview, editor_id = Ids.project_description_editor)



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
                text = if (hasIdPAram) "ID:$project_id" else "",
                modifier = Modifier
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(32.px)
                    .color(Theme.HalfBlack.rgb)
            )

            FieldComponent(
                tag = "title",
                inputField_id = Ids.project_title_field,
                placeHolder = "add title here",
                value = uiState.title
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
                editor_id = Ids.project_description_editor,
                preview_id = Ids.project_description_preview
            )

            EditorComponent(
                editor_id = Ids.project_description_editor,
                preview_id = Ids.project_description_preview,
                breakpoint = breakpoint,
                editorVisibility = uiState.editorVisibility
            )


            FieldComponent(
                tag = "Tech stack",
                inputField_id = Ids.project_techStack_field,
                placeHolder = "add tech stack here separated by ,",
                value = uiState.techStack
            )

            FieldComponent(
                tag = "Repo Link",
                inputField_id = Ids.project_repoLink_field,
                placeHolder = "add tech repo link here ",
                value = uiState.repoLink
            )

            FieldComponent(
                tag = "Video Link",
                inputField_id = Ids.project_videoLink_field,
                placeHolder = "add tech video Link here separated by ,",
                value = uiState.videoLink
            )


            FieldComponent(
                tag = "Main Image Link",
                inputField_id = Ids.project_mainImageLink_field,
                placeHolder = "add tech main Image Link here separated by ,",
                value = uiState.mainImageLink
            )




            SpanText(
                text = "Images List:",
                modifier = Modifier
                    .fillMaxWidth()
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(32.px)
                    .fontWeight(FontWeight.Bold)
            )

                SimpleGrid(
                    modifier = Modifier.fillMaxWidth(),
                    numColumns = numColumns(base = 5, md = 7, sm = 3),

                ){
                    if (uiState.imagesList.isNotEmpty()){
                        println(uiState.imagesList.first())
                        uiState.imagesList.forEach {imgUrl->
                            Image(
                                src = imgUrl,
                                modifier = ItemStyle.toModifier()
                                    .size(100.px)
                                    .margin(2.px)
                                    .onClick {
                                        uiState = uiState.copy(addToImageListPopup = imgUrl)
                                    }
                            )
                        }
                    }

                    Column(
                        modifier = ItemStyle.toModifier().size(100.px)
                            .borderRadius(r = 4.px)
                            .border(
                                width = 2.px,
                                style = LineStyle.Dotted,
                                color = Theme.PrimaryLight.rgb
                            )
                            .margin(all = 4.px)
                            .onClick {
                                     uiState = uiState.copy(addToImageListPopup = "new")
                            }
                        ,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        SpanText(
                            text = "Add New Image",
                            modifier = Modifier
                                .fontSize(18.px)
                                .fontWeight(FontWeight.Bold)
                        )
                    }

                }



            FieldComponent(
                tag = "Date",
                inputField_id = Ids.project_date_field,
                placeHolder = "add tech date here separated by ,",
                value = uiState.date
            )

            FinalButton(
                onClick = {
                    scope.launch {
                        if (hasIdPAram){
                            //updating
                            scope.launch {
                                val updatedProject = calculatingProjectPageValues().copy(imagesList = uiState.imagesList, _id = uiState._id)
                                    when(requestProjectDataUpdate(updatedProject)){
                                        true -> {
                                            uiState = uiState.copy(messagePopup = "Project Updated Successfully")
                                            delay(2000)
                                            uiState = uiState.copy(messagePopup = "")
                                            context.router.navigateTo(Screen.AdminProjects.route)
                                        }
                                        false -> {
                                            uiState = uiState.copy(messagePopup = "Error Project did not updated")
                                            delay(2000)
                                            uiState = uiState.copy(messagePopup = "")
                                        }
                                    }
                            }
                        }else{
                            //adding
                            val newProject = calculatingProjectPageValues().copy(imagesList = uiState.imagesList)
                                when(requestProjectDataAdd(newProject)){
                                    true -> {
                                        uiState = uiState.copy(messagePopup = "Project Added Successfully")
                                        delay(2000)
                                        uiState = uiState.copy(messagePopup = "")
                                        context.router.navigateTo(Screen.AdminProjects.route)
                                    }
                                    false -> {
                                        uiState = uiState.copy(messagePopup = "Error Project did not  be Added")
                                        delay(2000)
                                        uiState = uiState.copy(messagePopup = "")
                                    }
                                }

                        }
                    }

                },
                label = if (hasIdPAram) "Update" else "Add",
                color = if (hasIdPAram) Colors.Orange else Theme.PrimaryLight.rgb
            )




        }
        if (uiState.imagePopup){
            ControlPopup(
                editorControl = EditorControlIcons.Image,
                onDialogDismiss = { uiState = uiState.copy(imagePopup = false) },
                onAddClick = { url, desription ->
                    applyControlStyle(
                        editorControl = EditorControlIcons.Image,
                        onImageViewClicked = {
                            applyStyle(
                                ControlStyle.Image(
                                    selectedText = getSelectedText(editor_id = Ids.project_description_editor),
                                    imageUrl = url,
                                    alt = desription
                                ),
                                editor_id = Ids.project_description_editor,
                                preview_id = Ids.project_description_preview,
                            )
                        },
                        onLinkViewClicked = {},
                        editor_id = Ids.project_description_editor,
                        preview_id = Ids.project_description_preview
                    )
                }
            )
        }
        if (uiState.linkPopup){
            ControlPopup(
                editorControl = EditorControlIcons.Link,
                onDialogDismiss = { uiState = uiState.copy(linkPopup = false) },
                onAddClick = { url, desription ->
                    applyControlStyle(
                        editorControl = EditorControlIcons.Link,
                        onImageViewClicked = { },
                        onLinkViewClicked = {
                                            applyStyle(
                                                ControlStyle.Link(
                                                    selectedText = getSelectedText(editor_id = Ids.project_description_editor),
                                                    href = url,
                                                    title = desription
                                                ),
                                                editor_id = Ids.project_description_editor,
                                                preview_id = Ids.project_description_preview
                                            )
                        },
                        editor_id = Ids.project_description_editor,
                        preview_id = Ids.project_description_preview
                    )
                }
            )
        }
        if (uiState.addToImageListPopup.isNotEmpty()){
            ImageUrlPopup(
                onDialogDismiss = {
                    uiState = uiState.copy(addToImageListPopup = "")
                },
                onAddClick = {url->
                    val list: MutableList<String> = uiState.imagesList.toMutableList()
                    when(uiState.addToImageListPopup){
                        "new"->{
                            list.add(url)
                        }
                        else->{
                            val index = list.indexOf(uiState.addToImageListPopup)
                            list[index] = url
                        }
                    }
                    uiState = uiState.copy(imagesList = list.toList())


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











