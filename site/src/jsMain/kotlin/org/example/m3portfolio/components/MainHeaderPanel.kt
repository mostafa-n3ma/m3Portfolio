package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaX
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.styles.HeaderNavigationItemStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw
import org.w3c.dom.get
import org.w3c.dom.set


@Composable
fun MainHeaderPanel(
    content: @Composable ()->Unit
) {
    var overFlowMenuOpened by remember { mutableStateOf(false) }
    val breakpoint = rememberBreakpoint()


    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollBehavior(ScrollBehavior.Smooth)
        ) {
            HeaderPanel(
                breakpoint = breakpoint,
                onMenuClicked = {
                overFlowMenuOpened = !overFlowMenuOpened
            },
                overFlowMenuOpened = overFlowMenuOpened
            )

            if (overFlowMenuOpened){
                overFlowHeaderPanel(
                    onMenuClose = {
                        overFlowMenuOpened = !overFlowMenuOpened
                    },
                    content = {
                        // Header NavigationItems
                        HeaderNavigationItems(breakpoint)
                        LangDropDown()
                    },
                    breakpoint = breakpoint
                )
            }

            content()



        }

    }
}

@Composable
fun HeaderPanel(onMenuClicked: () -> Unit, breakpoint: Breakpoint, overFlowMenuOpened: Boolean) {
    if (breakpoint > Breakpoint.MD){
        //header Panel Internal
        HeaderPanelInternal(breakpoint = breakpoint)
    }else{
        // Collapsed Header Side Panel
        CollapseHeaderPanel(onMenuClicked = onMenuClicked,overFlowMenuOpened = overFlowMenuOpened)
    }
}

@Composable
fun CollapseHeaderPanel(onMenuClicked: () -> Unit, overFlowMenuOpened: Boolean) {
    var colorMode by ColorMode.currentState
    LaunchedEffect(Unit){
        val savedTheme  = localStorage[Constants.COLOR_MODE]?: ColorMode.LIGHT.name
        colorMode = ColorMode.valueOf(savedTheme)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Measurements.HEADER_HEIGHT.px)
            .padding(leftRight = 24.px)
            .backgroundColor(Theme.Them_bk_light.rgb),
    ){

        when(overFlowMenuOpened){
            true -> {
                FaX(
                    modifier = Modifier
                        .margin(right = 24.px)
                        .color(Theme.PrimaryLight.rgb)
                        .cursor(Cursor.Pointer)
                        .align(Alignment.CenterStart)
                        .onClick {
                            onMenuClicked()
                        },
                    size = IconSize.X1
                )
            }
            false -> {
                FaBars(
                    modifier = Modifier
                        .margin(right = 24.px)
                        .color(Theme.PrimaryLight.rgb)
                        .cursor(Cursor.Pointer)
                        .align(Alignment.CenterStart)
                        .onClick {
                            onMenuClicked()
                        },
                    size = IconSize.X1
                )
            }
        }

        SpanText(
            text = "Mostafa N3ma",
            modifier = Modifier
                .fontSize(24.px)
                .fontFamily(FONT_FAMILY)
                .align(Alignment.Center)
                .fontWeight(FontWeight.Bold)
                .color(Theme.PrimaryDark.rgb)
        )

        ColorMoodButton(
            colorMode = colorMode,
            onClick = {
                colorMode = colorMode.opposite
                localStorage[Constants.COLOR_MODE] = colorMode.name
            },
            modifier = Modifier.align(Alignment.CenterEnd)
            )
    }
}

@Composable
fun HeaderPanelInternal(breakpoint: Breakpoint) {
    var colorMode by ColorMode.currentState
    LaunchedEffect(Unit){
        val savedTheme  = localStorage[Constants.COLOR_MODE]?: ColorMode.LIGHT.name
        colorMode = ColorMode.valueOf(savedTheme)
    }
    Row(
        modifier = Modifier
            .padding(leftRight = 20.px)
            .width(100.vw)
            .height(54.px)
            .position(Position.Fixed)
            .zIndex(9)
            .backgroundColor(Theme.Them_bk_light.rgb),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        SpanText(
            text = "Mostafa N3ma",
            modifier = Modifier
                .fontSize(36.px)
                .fontFamily(FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .color(Theme.PrimaryDark.rgb)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.px),
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.px)
            ){
                HeaderNavigationItems(breakpoint = breakpoint)

            }


            LangDropDown()

            ColorMoodButton(
                colorMode = colorMode,
                onClick = {
                    colorMode = colorMode.opposite
                    localStorage[Constants.COLOR_MODE] = colorMode.name
                },

                )

        }



    }
}

@Composable
fun HeaderNavigationItems(breakpoint: Breakpoint) {
    val context = rememberPageContext()

        HeaderNavigationItem(
            modifier = Modifier.margin(right = 10.px),
            selected = context.route.path == "",
            title = "Home",
            onClick = {
                document.getElementById(Constants.MAIN_SECTION)?.scrollIntoView()
            },
            breakpoint = breakpoint
        )
        HeaderNavigationItem(
            modifier = Modifier.margin(right = 10.px),
            selected = context.route.path == "",
            title = "Projects",
            onClick = {
//                context.router.navigateTo("")
                document.getElementById(Constants.PROJECTS_SECTION)?.scrollIntoView()
            },
            breakpoint = breakpoint
        )
        HeaderNavigationItem(
            modifier = Modifier.margin(right = 10.px),
            selected = context.route.path == "",
            title = "Certificates",
            onClick = {
                document.getElementById(Constants.CERTIFICATES_SECTION)?.scrollIntoView()
            },
            breakpoint = breakpoint
        )
        HeaderNavigationItem(
            modifier = Modifier.margin(right = 10.px),
            selected = context.route.path == "",
            title = "About Me",
            onClick = {
                document.getElementById(Constants.ABOUT_ME_SECTION)?.scrollIntoView()
            },
            breakpoint = breakpoint
        )



}

@Composable
fun HeaderNavigationItem(
    modifier: Modifier = Modifier,
    selected : Boolean = false,
    title:String,
    onClick : ()->Unit,
    breakpoint: Breakpoint
) {
    SpanText(
        text = title,
        modifier = HeaderNavigationItemStyle.toModifier()
            .thenIf(
                condition = selected,
                other = Modifier
                    .color(Theme.PrimaryLight.rgb)
                    .textShadow(
                        offsetY = 0.px,
                        offsetX = 4.px,
                        blurRadius = 4.px,
                        color = Colors.Black
                    )
                    .fontFamily(FONT_FAMILY)
                    .fontSize(
                        if (breakpoint < Breakpoint.MD) 16.px
                        else 20.px
                    )
                    .fontWeight(FontWeight.Bold)
            )
            .onClick {
                onClick()
            }


    )
}


@Composable
fun overFlowHeaderPanel(
    onMenuClose : ()->Unit,
    content : @Composable ()->Unit,
    breakpoint: Breakpoint
) {
    val scope = rememberCoroutineScope()

    var translateY by remember { mutableStateOf((-100).percent) }
    var opacity by remember{ mutableStateOf(0.percent) }

    LaunchedEffect(key1 = breakpoint){
        translateY = 0.percent
        opacity = 100.percent
        if (breakpoint > Breakpoint.MD){
            scope.launch {
                translateY = (-100).percent
                opacity = 0.percent
                onMenuClose()
            }
        }
    }

    Column(
        modifier = Modifier
            .margin(top = 54.px )
            .padding( leftRight = 20.px, bottom = 20.px)
            .backgroundColor(Theme.Them_bk_light.rgb)
            .borderRadius(r = 4.px)
            .zIndex(9)
            .position(Position.Fixed)
            .overflow(Overflow.Auto)
            .translateY(translateY)
            .transition(Transition.of(property = "translate", duration = 500.ms))
            .onClick {

            }
    ){
       content()
    }
}
