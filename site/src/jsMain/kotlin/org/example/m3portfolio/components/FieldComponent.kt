package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Ids
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input

@Composable
fun FieldComponent(
    tag:String,
    inputField_id:String,
    placeHolder:String,
    value:String,
    height:Int=54
) {
    SpanText(
        text = "$tag:",
        modifier = Modifier
            .fillMaxWidth()
            .fontFamily(Constants.FONT_FAMILY)
            .fontSize(32.px)
            .fontWeight(FontWeight.Bold)
    )

    Input(
        type = InputType.Text,
        attrs = Modifier
            .id(inputField_id)
            .fillMaxWidth()
            .height(height.px)
            .margin(bottom = 12.px)
            .padding(leftRight = 20.px)
            .noBorder()
            .borderRadius(r = 5.px)
            .fontFamily(Constants.FONT_FAMILY)
            .fontSize(16.px)
            .backgroundColor(Theme.Item_bk_light.rgb)
            .toAttrs {
                attr("placeholder", placeHolder)
                attr("value", value )
            }
    )
}