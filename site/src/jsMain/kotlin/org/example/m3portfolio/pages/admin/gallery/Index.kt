package org.example.m3portfolio.pages.admin.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
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
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
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
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.components.FieldComponent
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.models.Gallery
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.util.ControllersHeader
import org.example.m3portfolio.util.InputType
import org.example.m3portfolio.util.ThumbnailUpLoader
import org.example.m3portfolio.util.getElementValue
import org.example.m3portfolio.util.isUserLoggedIn
import org.example.m3portfolio.util.requestDeletingGalleries
import org.example.m3portfolio.util.requestGalleryAdd
import org.example.m3portfolio.util.requestGalleryData
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.w3c.dom.HTMLInputElement


@Page
@Composable
fun GalleryPage() {
    isUserLoggedIn {
        GalleryScreen()
    }
}

@Composable
fun GalleryScreen() {
    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    var galleryImagesList by remember {
        mutableStateOf(listOf<Gallery>())
    }

    val updateGalleryList = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(updateGalleryList.value) {
        requestGalleryData(
            onSuccess = { result ->
                updateGalleryList.value = false
                galleryImagesList = result
                println("gallery result:$result")
            },
            onError = {
                println("from GalleryScreen:requestGalleryData:onError:${it.message}")
            }
        )

    }


    AdminPanelLayout {
        GalleryScreenContent(
            context = context,
            galleryImagesList = galleryImagesList,
            breakpoint = breakpoint,
            scope = scope,
            onUpdate = {
                updateGalleryList.value = true
            }
        )
    }


}


data class GalleryImageUiStat(
    var sideSheetOpened: Boolean = false,
    var category: String = "",
    var base_64: String = ""
) {
    fun flash() {
        sideSheetOpened = false
        category = ""
        base_64 = ""
    }
}

@Composable
fun GalleryScreenContent(
    context: PageContext,
    galleryImagesList: List<Gallery>,
    breakpoint: Breakpoint,
    scope: CoroutineScope,
    onUpdate: (Boolean) -> Unit
) {
    var selectableMode by remember { mutableStateOf(false) }
    val selectedGalleries = remember { mutableStateListOf<String>() }
    var messagePopup by remember { mutableStateOf("") }

    val categories: List<String> = galleryImagesList.map { it.category }.distinct()

    val uiState: MutableState<GalleryImageUiStat> =
        remember { mutableStateOf(GalleryImageUiStat()) }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SideGalleryAddSheet(
            modifier = Modifier.align(Alignment.TopEnd),
            onAddClicked = {
                onUpdate(true)
                uiState.value = uiState.value.copy(
                    sideSheetOpened = false
                )
            },
            onCancelClicked = {

            },
            uiState = uiState
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(left = if (breakpoint > Breakpoint.MD) (Measurements.SIDE_PANEL_WIDTH + 20).px else 20.px),
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
                addBtnLabel = "Add New Image",
                onAddDeleteBtnClicked = {
                    if (selectableMode) {
                        // deleting selected Images
                        println("clicking delete btn")

                        scope.launch {
                            val galleriesIsDeleted =
                                requestDeletingGalleries(selectedGalleries)
                            if (galleriesIsDeleted) {
                                messagePopup = "Selected Images Deleted Successfully"
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
                        // adding new image
                        uiState.value = uiState.value.copy(sideSheetOpened = true)
                        println("clicking add btn")

                    }
                }
            )

            categories.forEach { category ->
                SpanText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fontSize(32.px)
                        .fontFamily(FONT_FAMILY)
                        .margin(leftRight = 20.px),
                    text = category
                )
                val filteredList: List<Gallery> =
                    galleryImagesList.filter { it.category == category }
                SimpleGrid(
                    numColumns = numColumns(base = 2, md = 3, lg = 4, xl = 5),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    filteredList.forEach { galleryItem ->
                        Box(
                            modifier = Modifier
                                .padding(10.px)
                                .margin(all = 5.px)
                                .borderRadius(
                                    r = 4.px
                                )
                                .backgroundColor(Theme.Them_bk_light.rgb)
                                .border(
                                    width = if (selectableMode) 3.px else 1.px,
                                    style = if (selectableMode) {
                                        if (selectedGalleries.contains(galleryItem._id)) {
                                            LineStyle.Solid
                                        } else {
                                            LineStyle.Dotted
                                        }
                                    } else {
                                        LineStyle.Solid
                                    },
                                    color = if (selectableMode) {
                                        if (selectedGalleries.contains(galleryItem._id)) {
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
                                        if (selectedGalleries.contains(galleryItem._id)) {
                                            selectedGalleries.remove(galleryItem._id)
                                        } else {
                                            selectedGalleries.add(galleryItem._id)
                                        }

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
                            Image(
                                modifier = Modifier
                                    .size(200.px)
                                    .styleModifier {
                                        property("object-fit", "contain")
                                    },
                                src = galleryItem.base_64
                            )
                        }

                    }
                }

            }


        }
    }


}

@Composable
fun SideGalleryAddSheet(
    modifier: Modifier = Modifier,
    onAddClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    uiState: MutableState<GalleryImageUiStat>
) {
    val scope = rememberCoroutineScope()
    var translateX by remember { mutableStateOf(100) }
    LaunchedEffect(uiState.value.sideSheetOpened) {
        translateX = when (uiState.value.sideSheetOpened) {
            true -> 0
            false -> 100
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth(50.vw)
            .fillMaxHeight()
            .padding(leftRight = 30.px)
            .backgroundColor(Theme.Them_bk_light.rgb)
            .zIndex(20)
            .borderRadius(r = 5.px)
            .translateX(translateX.percent)
            .transition(
                Transition.of(property = TransitionProperty.All.toString(), duration = 300.ms)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .margin(20.px),
            verticalArrangement = Arrangement.spacedBy(20.px, alignment = Alignment.Top)
        ) {
            FieldComponent(
                tag = "Category",
                inputField_id = Ids.gallery_category_field,
                placeHolder = "add Category here",
                value = uiState.value.category
            )
            ThumbnailUpLoader(
                field_id = Ids.gallery_base64_field,
                thumbnail = "",
                thumbnailInputDisabled = true,
                onThumbnailSelect = { fileName, file ->
                    (document.getElementById(Ids.gallery_base64_field) as HTMLInputElement).value =
                        fileName
                    // save the file in the thumbnail
                    uiState.value = uiState.value.copy(base_64 = file)
                }
            )

            FinalButton(
                onClick = {
                    val category: String =
                        getElementValue(Ids.gallery_category_field, InputType.InputField)
                    val base64 = uiState.value.base_64
                    println("add btn test ::category = $category // base64 = $base64")
                    scope.launch {
                        val newGalleryImgAdd: Boolean =
                            if (category.isNotEmpty() && base64.isNotEmpty()) {
                                onAddClicked()
                                requestGalleryAdd(
                                    Gallery(
                                        category = category,
                                        base_64 = base64
                                    )
                                )
                            } else {
                                println("please fill the fields (category, base64")
                                false
                            }


                    }

                },
                label = "Add",
                color = Theme.PrimaryLight.rgb,
                textColor = Colors.White
            )
            FinalButton(
                onClick = {
                    uiState.value = uiState.value.copy(sideSheetOpened = false)
                },
                label = "Cancel",
                color = Colors.White,
                textColor = Theme.PrimaryLight.rgb
            )


        }
    }
}
