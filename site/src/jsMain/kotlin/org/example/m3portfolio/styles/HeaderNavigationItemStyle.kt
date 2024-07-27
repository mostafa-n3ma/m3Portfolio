package org.example.m3portfolio.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.models.Theme
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val HeaderNavigationItemStyle = CssStyle {
    base {
        Modifier
            .fontSize(24.px)
            .fontFamily(FONT_FAMILY)
            .fontWeight(FontWeight.Normal)
            .color(Theme.PrimaryLight.rgb)
            .transition(
                Transition.of(
                    property = TransitionProperty.All.toString(),
                    duration = 300.ms
                )
            )
    }
    hover{
       Modifier
           .fontWeight(FontWeight.Bold)
           .fontSize(32.px)
           .cursor(Cursor.Pointer)
    }
}