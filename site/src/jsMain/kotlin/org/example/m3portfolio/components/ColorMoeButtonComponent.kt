package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.m3portfolio.Res
import org.jetbrains.compose.web.css.px

@Composable
fun ColorMoodButton(
    colorMode: ColorMode,
    onClick  : ()->Unit,
    modifier: Modifier = Modifier
) {

    Image(
        modifier = modifier
            .size( 32.px)
            .margin()
            .onClick {
                onClick()
            }
            .cursor(Cursor.Pointer)
        ,
        src = if (colorMode.isDark) Res.Icon.moon
        else Res.Icon.sun
    )
}
