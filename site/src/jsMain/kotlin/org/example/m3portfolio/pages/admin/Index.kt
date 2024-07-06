package org.example.m3portfolio.pages.admin

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.m3portfolio.components.AdminPanelLayout
import org.example.m3portfolio.util.isUserLoggedIn
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    isUserLoggedIn {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    AdminPanelLayout {
        SpanText(text = "side panel")
    }
}
