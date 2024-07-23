package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text


@Composable
fun FinalButton(
    onClick: () -> Unit,
    label: String,
    color: CSSColorValue,
    textColor:CSSColorValue = Colors.White,
    modifier :Modifier = Modifier.fillMaxWidth()
) {
    val s: Modifier = Modifier.fillMaxWidth()
    Button(
        attrs = modifier
            .onClick { onClick() }
            .height(54.px)
            .backgroundColor(color)
            .color(Colors.White)
            .fontFamily(FONT_FAMILY)
            .fontSize(16.px)
            .borderRadius(r = 4.px)
            .noBorder()
            .toAttrs()
    ) {
        SpanText(
            text = label,
            modifier = Modifier
                .color(textColor)

        )
    }
}