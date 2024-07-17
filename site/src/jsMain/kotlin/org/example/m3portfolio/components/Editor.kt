package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onKeyDown
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids.info_Bio_editor
import org.example.m3portfolio.Ids.info_Bio_preview
import org.example.m3portfolio.models.ControlStyle
import org.example.m3portfolio.models.EditorControlIcons
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.styles.keyEditorStyle
import org.example.m3portfolio.styles.previewBtnStyle
import org.example.m3portfolio.util.applyControlStyle
import org.example.m3portfolio.util.applyStyle
import org.example.m3portfolio.util.getEditor
import org.example.m3portfolio.util.getSelectedText
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.TextArea


@Composable
fun EditorComponent(editor_id: String,
                    preview_id: String,
                    editorVisibility: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        TextArea(
            attrs = Modifier
                .id(editor_id)
                .fillMaxWidth()
                .height(400.px)
                .maxHeight(400.px)
                .resize(Resize.None)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(Theme.Item_bk_light.rgb)
                .borderRadius(r = 4.px)
                .noBorder()
                .visibility(
                    if (editorVisibility) Visibility.Visible
                    else Visibility.Hidden
                )
                .onKeyDown {
                    // to catch the key pressed on the keyboard
                    // see the Break ControlStyle
                    // this code will add the break tag <br> to the div element to make new line to the text
                    if (it.code == "Enter") {
                        applyStyle(
                            ControlStyle.Break(selectedText = getSelectedText(editor_id)),
                            editor_id = editor_id,
                            preview_id = preview_id,
                        )
                    }
                }
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .toAttrs {
                    attr("placeholder", "type here...")
                }
        )


        Div(
            attrs = Modifier
                .id(preview_id)
                .fillMaxWidth()
                .height(400.px)
                .maxHeight(400.px)
                .resize(Resize.None)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(Theme.Item_bk_light.rgb)
                .borderRadius(r = 4.px)
                .noBorder()
                .visibility(
                    if (editorVisibility) Visibility.Hidden
                    else Visibility.Visible
                )
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .toAttrs()
        ) {

        }

    }
}

@Composable
fun EditorControllersPanel(
    breakpoint: Breakpoint,
    editor_id: String,
    preview_id: String,
    editorVisibility: Boolean,
    onEditorVisibilityChanged: () -> Unit,
    onLinkViewClicked: () -> Unit,
    onImageViewClicked: () -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth()){
        SimpleGrid(numColumns = numColumns(base = 1, md = 2), modifier = Modifier.fillMaxWidth()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.px)
                    .borderRadius(r = 4.px)
                    .backgroundColor(Colors.White)
            ){
                EditorControlIcons.values().forEach {
                    EditorControlView(
                        control = it,
                        onClick = {
                            applyControlStyle(
                                editorControl = it,
                                onLinkViewClicked = onLinkViewClicked,
                                onImageViewClicked = onImageViewClicked,
                                editor_id = editor_id,
                                preview_id = preview_id
                            )
                        }
                    )
                }

            }

            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    attrs = previewBtnStyle.toModifier()
                        .height(54.px)
                        .thenIf(
                            condition = breakpoint < Breakpoint.MD,
                            other = Modifier.fillMaxWidth()
                        )
                        .margin(topBottom = if (breakpoint < Breakpoint.MD ) 12.px else 0.px)
                        .padding(leftRight = 24.px)
                        .borderRadius(r = 4.px)
                        .backgroundColor(
                            if (editorVisibility) Theme.Item_bk_light.rgb
                            else Theme.PrimaryLight.rgb
                        )
                        .color(
                            if (editorVisibility) Theme.PrimaryLight.rgb
                            else Colors.White
                        )
                        .noBorder()
                        .onClick {
                            onEditorVisibilityChanged()
                            document.getElementById(info_Bio_preview)?.innerHTML = getEditor(info_Bio_editor).value
                            // a fun to actually write and run js code here
                            // it will be cared by kotlin and run on the website as js code
                            // so the next line of code in js is to run and init the highlight.js library from the preview button
//                            js("hljs.highlightAll()") as Unit
                        }
                        .toAttrs()
                ){
                    SpanText(
                        modifier = Modifier
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Medium)
                            .fontSize(14.px),
                        text = "Preview"
                    )
                }
            }








        }









    }



}



@Composable
fun EditorControlView(
    control:EditorControlIcons,
    onClick: () -> Unit
) {
    Box (
        modifier = keyEditorStyle.toModifier()
            .fillMaxHeight()
            .padding(leftRight = 12.px)
            .borderRadius(r = 4.px)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.size(24.px),
            src = control.icon,
            description = "${control.name} icon",
        )
    }
}
