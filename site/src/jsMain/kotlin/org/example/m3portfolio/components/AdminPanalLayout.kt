package org.example.m3portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.m3portfolio.Measurements.PAGE_WIDTH
import org.jetbrains.compose.web.css.px


@Composable
fun AdminPanelLayout(
    content: @Composable () -> Unit
) {
    var overFlowMenuOpened by remember { mutableStateOf(false) }
    val breakpoint = rememberBreakpoint()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .maxWidth(PAGE_WIDTH.px)
        ) {

            SidePanel(onMenuClicked = {
                overFlowMenuOpened = true
            })

            if (overFlowMenuOpened) {
                OverFlowSidePanel(onMenuClose = {
                    overFlowMenuOpened = false
                },
                    content = { NavigationItems(breakpoint) }
                )
            }



            content()


        }
    }


}