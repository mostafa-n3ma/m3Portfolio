package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.attr
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids.linkHrefInput
import org.example.m3portfolio.Ids.linkTitleInput
import org.example.m3portfolio.models.EditorControlIcons
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement

@Composable
fun MessagePopup(
    message:String,
    onDialogDismiss:()->Unit
) {
    val scope = rememberCoroutineScope()
    var color by remember { mutableStateOf(Colors.White) }
    scope.launch {
       repeat(5){
           delay(200)
           color = Colors.Transparent
           delay(200)
           color = Colors.White
           delay(200)
           color = Colors.Transparent
           delay(200)
           color = Colors.White
       }
    }
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.TopCenter
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .onClick { onDialogDismiss() },
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .backgroundColor(Colors.Gray)
                    .borderRadius(r= 4.px),

            ){
                SpanText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .color(color)
                        .textAlign(TextAlign.Center)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(48.px),
                    text = message
                )
            }
        }





    }
}


@Composable
fun ControlPopup(
    editorControl: EditorControlIcons,
    onDialogDismiss: () -> Unit,
    onAddClick: (String, String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(Theme.HalfBlack.rgb)
                .onClick { onDialogDismiss() }
        )
        Column(
            modifier = Modifier
                .width(500.px)
                .padding(all = 24.px)
                .backgroundColor(Colors.White)
                .borderRadius(r = 4.px)
        ) {
            Input(
                type = InputType.Text,
                attrs = Modifier
                    .id(linkHrefInput)
                    .fillMaxWidth()
                    .height(54.px)
                    .padding(left = 20.px)
                    .margin(bottom = 12.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .noBorder()
                    .borderRadius(r = 4.px)
                    .backgroundColor(Theme.Item_bk_light.rgb)
                    .toAttrs {
                        attr(
                            "placeholder",
                            if (editorControl == EditorControlIcons.Link)"Href" else "Image URL"
                        )
                    }
            )
            Input(
                type = InputType.Text,
                attrs = Modifier
                    .id(linkTitleInput)
                    .fillMaxWidth()
                    .height(54.px)
                    .padding(left = 20.px)
                    .margin(bottom = 20.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .noBorder()
                    .borderRadius(r = 4.px)
                    .backgroundColor(Theme.Item_bk_light.rgb)
                    .toAttrs {
                        attr(
                            "placeholder",
                            if (editorControl == EditorControlIcons.Link)"Title" else "Description"
                        )
                    }
            )
            Button(
                attrs = Modifier
                    .onClick {
                        val href =
                            (document.getElementById(linkHrefInput) as HTMLInputElement).value
                        val title =
                            (document.getElementById(linkTitleInput) as HTMLInputElement).value
                        onAddClick(href, title)
                        onDialogDismiss()
                    }
                    .fillMaxWidth()
                    .height(54.px)
                    .borderRadius(r = 4.px)
                    .backgroundColor(Theme.PrimaryLight.rgb)
                    .color(Colors.White)
                    .noBorder()
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .toAttrs()
            ) {
                SpanText(text = "Add")
            }
        }
    }
}


@Composable
fun ImageUrlPopup(
    onDialogDismiss: () -> Unit,
    onAddClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(Theme.HalfBlack.rgb)
                .onClick { onDialogDismiss() }
        )
        Column(
            modifier = Modifier
                .width(500.px)
                .padding(all = 24.px)
                .backgroundColor(Colors.White)
                .borderRadius(r = 4.px)
        ) {
            Input(
                type = InputType.Text,
                attrs = Modifier
                    .id(linkHrefInput)
                    .fillMaxWidth()
                    .height(54.px)
                    .padding(left = 20.px)
                    .margin(bottom = 12.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .noBorder()
                    .borderRadius(r = 4.px)
                    .backgroundColor(Theme.Item_bk_light.rgb)
                    .toAttrs {
                        attr(
                            "placeholder",
                             "Image URL"
                        )
                    }
            )
            Button(
                attrs = Modifier
                    .onClick {
                        val url =
                            (document.getElementById(linkHrefInput) as HTMLInputElement).value
                        onAddClick(url)
                        onDialogDismiss()
                    }
                    .fillMaxWidth()
                    .height(54.px)
                    .borderRadius(r = 4.px)
                    .backgroundColor(Theme.PrimaryLight.rgb)
                    .color(Colors.White)
                    .noBorder()
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .toAttrs()
            ) {
                SpanText(text = "Add")
            }
        }
    }
}