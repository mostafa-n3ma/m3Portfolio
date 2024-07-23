package org.example.m3portfolio.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.example.m3portfolio.models.Theme
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val ButtonsStyle = CssStyle {
    base {
        Modifier
            .width(300.px)
            .height(54.px)
            .borderRadius(r=4.px)
            .backgroundColor(Theme.PrimaryLight.rgb)
            .transition(
                Transition.of(
                    property = TransitionProperty.All.toString(),
                    duration = 500.ms
                )
            )
    }

    hover{
        Modifier
            .width(320.px)
            .height(70.px)
            .borderRadius(r=4.px)
            .backgroundColor(Theme.PrimaryLight.rgb)
            .cursor(Cursor.Pointer)
    }
}