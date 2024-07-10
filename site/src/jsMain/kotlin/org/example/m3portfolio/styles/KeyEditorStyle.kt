package org.example.m3portfolio.styles

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.example.m3portfolio.models.Theme
import org.jetbrains.compose.web.css.ms

val keyEditorStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Colors.Transparent)
            .transition(
                Transition.of(
                    property = TransitionProperty.All.toString(),
                    duration = 500.ms
                )
            )
    }

    hover {
        Modifier
            .backgroundColor(Theme.PrimaryLight.rgb)
    }

}


val previewBtnStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Theme.Item_bk_light.rgb)
            .transition(
                Transition.of(
                    property = TransitionProperty.All.toString(),
                    duration = 500.ms
                )
            )
    }

    hover {
        Modifier
            .backgroundColor(Theme.PrimaryLight.rgb)
            .color(Colors.White)
    }
}