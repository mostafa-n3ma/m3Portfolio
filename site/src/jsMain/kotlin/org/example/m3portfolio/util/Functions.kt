package org.example.m3portfolio.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.rememberPageContext
import kotlinx.browser.localStorage
import org.example.m3portfolio.navigation.Screen
import org.w3c.dom.get

@Composable
fun isUserLoggedIn(content: @Composable () -> Unit) {

    val context = rememberPageContext()
    val remembered = remember { localStorage["remember"].toBoolean() }
    val userId = remember { localStorage["userId"] }

    var userIdExist by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = Unit) {
        userIdExist = if (!userId.isNullOrEmpty()) requestIdCheck(id = userId) else false
        if (!remembered || !userIdExist) {
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    }

    if (remembered && userIdExist) {
        content()
    } else {
        println("Loading....")
    }
}