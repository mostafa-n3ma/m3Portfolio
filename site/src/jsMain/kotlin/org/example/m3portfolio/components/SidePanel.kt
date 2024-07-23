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
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Measurements.COLLAPSED_PANEL_HEIGHT
import org.example.m3portfolio.Measurements.SIDE_PANEL_WIDTH
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.NavigationItemStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh


@Composable
fun SidePanel(
    onMenuClicked : ()-> Unit)
{

    var breakpoint = rememberBreakpoint()
    if (breakpoint >Breakpoint.MD){
        SidePanelInternal(breakpoint = breakpoint)
    }else{
        CollapsedSidePanel(onMenuClicked = onMenuClicked)
    }

}




@Composable
fun SidePanelInternal(breakpoint: Breakpoint) {
        Column(
            modifier = Modifier
                .padding(leftRight = 40.px, top = 50.px)
                .width(SIDE_PANEL_WIDTH.px)
                .height(100.vh)
                .position(Position.Fixed)
                .zIndex(9)
                .backgroundColor(Theme.PrimaryLight.rgb)
        ){

            NavigationItems(breakpoint = breakpoint)


        }

}

@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    title: String,
    onClick: () -> Unit,
    breakpoint: Breakpoint
) {
    SpanText(
        text = title,
        modifier = NavigationItemStyle.toModifier()
            .thenIf(
                condition = selected,
                other = Modifier
                    .color(Theme.PrimaryDark.rgb)
                    .textShadow(
                        offsetX = 0.px ,
                        offsetY = 4.px ,
                        blurRadius = 4.px ,
                        color = Colors.White
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
fun NavigationItems(breakpoint: Breakpoint) {
    val context = rememberPageContext()
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminHome.route,
        title = "Info",
        onClick = {
            context.router.navigateTo(Screen.AdminHome.route)
        }
        ,breakpoint = breakpoint
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminExperience.route,
        title = "Experience",
        onClick = {
            context.router.navigateTo(Screen.AdminExperience.route)
        },
        breakpoint = breakpoint
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminProjects.route,
        title = "Projects",
        onClick = {
            context.router.navigateTo(Screen.AdminProjects.route)
        },
        breakpoint = breakpoint
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminCertificates.route,
        title = "Certificates",
        onClick = {
            context.router.navigateTo(Screen.AdminCertificates.route)
        },
        breakpoint = breakpoint
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminWebsites.route,
        title = "Websites",
        onClick = {
            context.router.navigateTo(Screen.AdminWebsites.route)
        },
        breakpoint = breakpoint
    )


}


@Composable
fun CollapsedSidePanel(onMenuClicked: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(COLLAPSED_PANEL_HEIGHT.px)
                .padding(leftRight = 24.px)
                .backgroundColor(Theme.PrimaryLight.rgb),
            verticalAlignment = Alignment.CenterVertically
        ){
            FaBars(
                modifier = Modifier
                    .margin(right = 24.px)
                    .color(Colors.White)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        onMenuClicked()
                    },
                size = IconSize.X1
            )
        }
}





@Composable
fun OverFlowSidePanel(
    onMenuClose: () -> Unit,
    content: @Composable () -> Unit
) {
    val breakPoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    var translateX by remember { mutableStateOf((-100).percent) }
    var opcity by remember { mutableStateOf(0.percent) }
    LaunchedEffect(key1 = breakPoint) {
        translateX = 0.percent
        opcity = 100.percent
        if (breakPoint > Breakpoint.MD) {
            scope.launch {
                translateX = (-100).percent
                opcity = 0.percent
                delay(300)
                onMenuClose()
            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(9)
            .opacity(opcity)
            .transition(Transition.of(property = "opacity", duration = 300.ms))
            .backgroundColor(Theme.HalfBlack.rgb)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(leftRight = 24.px)
                .width(if (breakPoint < Breakpoint.MD) 50.percent else 25.percent)
                .translateX(translateX)
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .transition(Transition.of(property = "translate", duration = 300.ms))
                .backgroundColor(Theme.PrimaryLight.rgb)
        ) {
            Row(
                modifier = Modifier
                    .margin(bottom = 60.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaXmark(
                    size = IconSize.LG,
                    modifier = Modifier
                        .margin(20.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick {

                            scope.launch {
                                translateX = (-100).percent
                                opcity = 0.percent
                                delay(300)
                                onMenuClose()
                            }
                        }
                )

            }
//            NavigationItems()
            content()
        }
    }

}



