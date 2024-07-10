package org.example.m3portfolio.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.core.rememberPageContext
import kotlinx.browser.document
import kotlinx.browser.localStorage
import org.example.m3portfolio.navigation.Screen
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
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


fun Modifier.noBorder():Modifier{
    return this
        .border(
            width = 0.px,
            style = LineStyle.None,
            color = Colors.Transparent
        )
        .outline(
            width = 0.px,
            style = LineStyle.None,
            color = Colors.Transparent
        )
}

enum class InputType{
    TextArea,
    InputField
}
fun getElementValue(id:String,type:InputType):String{
    return when(type){
        InputType.TextArea -> {
            (document.getElementById(id) as HTMLTextAreaElement).value
        }
        InputType.InputField -> {
            (document.getElementById(id) as HTMLInputElement).value
        }
    }
}

