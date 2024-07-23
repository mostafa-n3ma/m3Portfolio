package org.example.m3portfolio.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba

val ItemStyle = CssStyle {
    base {
        Modifier
            .scale(100.percent)
            .transition(
                Transition.of(
                    property = TransitionProperty.All.toString(),
                    duration = 500.ms
                )
            )
    }
    hover{
        Modifier
            .boxShadow(
                offsetX = 0.px,
                offsetY = 0.px,
                blurRadius = 8.px,
                spreadRadius = 6.px,
                color = rgba(0,0,0,0.06)
            )
            .scale(105.percent)
            .cursor(Cursor.Pointer)
    }
}
