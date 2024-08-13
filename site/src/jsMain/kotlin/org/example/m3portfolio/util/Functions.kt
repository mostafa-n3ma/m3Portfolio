package org.example.m3portfolio.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.file.loadDataUrlFromDisk
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.attr
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.AttrsModifier
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.StyleModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.disabled
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids
import org.example.m3portfolio.components.FinalButton
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.ItemStyle
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.get

@Composable
fun isUserLoggedIn(content: @Composable () -> Unit) {

    val context = rememberPageContext()
    val remembered = remember { localStorage["remember"].toBoolean() }
    val userId = remember { localStorage["userId"] }

    var userIdExist by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = Unit) {
        userIdExist = if (!userId.isNullOrEmpty()) requestIdCheck(id = userId) else false
        if (!remembered || !userIdExist) {
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    }

    if (remembered && userIdExist) {
        content()
    } else {
        println("Loading....")
    }
}


fun Modifier.noBorder(): Modifier {
    return this
        .border(
            width = 0.px,
            style = LineStyle.None,
            color = Colors.Transparent
        )
        .outline(
            width = 0.px,
            style = LineStyle.None,
            color = Colors.Transparent
        )
}

enum class InputType {
    TextArea,
    InputField
}

fun getElementValue(id: String, type: InputType): String {
    return when (type) {
        InputType.TextArea -> {
            (document.getElementById(id) as HTMLTextAreaElement).value
        }

        InputType.InputField -> {
            (document.getElementById(id) as HTMLInputElement).value
        }
    }
}


@Composable
fun ControllersHeader(
    selectableMode: Boolean,
    selectBtnLabel: String = "Select",
    onSelectBtnClicked: () -> Unit,
    addBtnLabel: String = "Add",
    onAddDeleteBtnClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.px)
            .padding(leftRight = 20.px),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.px, Alignment.End)
    ) {
        FinalButton(
            onClick = {
                onSelectBtnClicked()
            },
            label = selectBtnLabel,
            color = Theme.Them_bk_light.rgb,
            modifier = ItemStyle
                .toModifier()
                .width(200.px),
            textColor = Theme.PrimaryLight.rgb
        )

        FinalButton(
            onClick = {
                onAddDeleteBtnClicked()

            },
            label = when (selectableMode) {
                true -> "Delete"
                false -> addBtnLabel
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
}


@Composable
fun ThumbnailUpLoader(
    field_id: String,
    thumbnail: String,
    thumbnailInputDisabled: Boolean,
    onThumbnailSelect: (String, String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.px)
    ) {
        Input(
            type = org.jetbrains.compose.web.attributes.InputType.Text,
            attrs = Modifier
                .id(field_id)
                .margin(right = 4.px)
                .fillMaxSize()
                .padding(leftRight = 20.px)
                .backgroundColor(
                    if (thumbnailInputDisabled) Colors.LightGray
                    else Colors.White
                )
                .noBorder()
                .borderRadius(4.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .thenIf(
                    condition = thumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs {
                    attr("placeholder", "Thumbnail")
                    attr("value", thumbnail)
                }
        )

        Button(
            attrs = Modifier
                // the  wey uploading files in kobweb from the device
                .onClick {
                    document.loadDataUrlFromDisk(
                        accept = "image/png, image/jpeg",
                        onLoad = {
                            onThumbnailSelect(filename, it)
                        }
                    )
                }
                .fillMaxHeight()
                .padding(leftRight = 24.px)
                .backgroundColor(if (!thumbnailInputDisabled) Colors.Gray else Theme.PrimaryLight.rgb)
                // color here is for the text in the button
                .color(if (!thumbnailInputDisabled) Colors.LightGray else Colors.White)
                .noBorder()
                .borderRadius(4.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(14.px)
                .fontWeight(FontWeight.Medium)
                .thenIf(
                    condition = !thumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs()
        ) {
            SpanText(text = "Upload")
        }
    }
}


