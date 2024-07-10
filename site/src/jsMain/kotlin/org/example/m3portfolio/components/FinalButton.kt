package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text


@Composable
fun FinalButton(onClick: () -> Unit, text: String,color: Color.Rgb) {
    Button(
        attrs = Modifier
            .onClick { onClick() }
            .fillMaxWidth()
            .height(54.px)
            .backgroundColor(color)
            .color(Colors.White)
            .fontFamily(FONT_FAMILY)
            .fontSize(16.px)
            .borderRadius(r = 4.px)
            .noBorder()
            .toAttrs()
    ) {
        Text(text)
    }
}