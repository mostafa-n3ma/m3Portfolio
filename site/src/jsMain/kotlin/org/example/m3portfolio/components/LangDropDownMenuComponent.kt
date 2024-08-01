package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaAngleDown
import com.varabyte.kobweb.silk.components.icons.fa.FaAngleUp
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.localStorage
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Res
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.w3c.dom.get
import org.w3c.dom.set


@Composable
fun LangDropDown(
    onLanguageChange:(Constants.Languages)->Unit
) {
    var selectedLanguage: String by remember {
        mutableStateOf(
            localStorage[Constants.LANGUAGE_STORAGE_VALUE] ?: Constants.Languages.EN.name
        )
    }
    localStorage[Constants.LANGUAGE_STORAGE_VALUE] = selectedLanguage

    Row(
        modifier = Modifier
            .classNames("dropdown")
            .padding(leftRight = 7.px)
            .borderRadius(25.px)
            .cursor(Cursor.Pointer)
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = Colors.Black
            )
            .attrsModifier {
                attr("data-bs-toggle", "dropdown")
            },
        horizontalArrangement = Arrangement.spacedBy(5.px),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            src = Res.Image.language,
            modifier = Modifier
                .size(16.px)
        )
        SpanText(
            text = selectedLanguage,
            modifier = Modifier
                .fontSize(16.px)
                .fontFamily(Constants.FONT_FAMILY)
                .fontWeight(FontWeight.Normal)
                .color(Colors.Black)
        )

        Ul(
            attrs = Modifier
                .classNames("dropdown-menu")
                .fillMaxWidth()
                .toAttrs()
        ) {
            Constants.Languages.values().forEach { LangItem ->
                Li(
                    attrs = Modifier.fillMaxWidth().toAttrs()
                ) {
                    A(
                        attrs = Modifier
                            .classNames("dropdown-item")
                            .color(Colors.Black)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(16.px)
                            .onClick {
                                selectedLanguage = LangItem.name
                                onLanguageChange(LangItem)
                            }
                            .toAttrs()
                    ) {
                        Text(value = LangItem.name)
                    }
                }
            }
        }



        FaAngleDown(
            modifier = Modifier.color(Colors.Black),
            size = IconSize.X1,

        )
//        FaAngleUp(
//            size = IconSize.X1
//        )


    }
}