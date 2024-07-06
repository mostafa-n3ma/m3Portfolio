package org.example.m3portfolio.styles

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.example.m3portfolio.Constants
import org.example.m3portfolio.models.Theme
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val NavigationItemStyle = CssStyle {
    base {
        Modifier
            .fontFamily(Constants.FONT_FAMILY)
            .fontSize(16.px)
            .color(Theme.Item_bk_light.rgb)
            .transition(
                Transition.of(
                    property = TransitionProperty.All.toString(),
                    duration = 500.ms
                )
            )

    }

    hover {
        Modifier
            .textShadow(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 4.px,
                color = Colors.White
            )
            .fontFamily(Constants.FONT_FAMILY)
            .fontSize(32.px)
            .margin(topBottom = 8.px)
            .fontWeight(FontWeight.Bold)
            .cursor(Cursor.Pointer)
            .color(Theme.PrimaryDark.rgb)

    }
}