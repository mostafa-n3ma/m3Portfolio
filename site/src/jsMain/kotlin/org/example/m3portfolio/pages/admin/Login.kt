package org.example.m3portfolio.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Constants.REMEMBERED_STORAGE_VALUE
import org.example.m3portfolio.Constants.USER_ID_STORAGE_VALUE
import org.example.m3portfolio.Constants.USER_NAME_STORAGE_VALUE
import org.example.m3portfolio.Ids.login_passweordInput
import org.example.m3portfolio.Ids.login_userNameInput
import org.example.m3portfolio.Res
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.models.User
import org.example.m3portfolio.models.UserWithoutPassword
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.LoginInputStyle
import org.example.m3portfolio.util.requestUserCheck
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.set

@Page
@Composable
fun LoginScreen() {
    var errorText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .backgroundColor(Theme.Them_bk_light.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier
                    .margin(bottom = 50.px)
                    .width(100.px)
                    .borderRadius(r = 10.px)
                ,
                src = Res.Image.my_image,
                description = "my image mostafa nema"
            )

            Input(
                type = InputType.Text,
                attrs = LoginInputStyle.toModifier()
                    .id(login_userNameInput).margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontSize(14.px)
                    .fontFamily(FONT_FAMILY)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .toAttrs {
                        attr("placeholder", "Username")
                    }
            )

            Input(
                type = InputType.Password,
                attrs = LoginInputStyle.toModifier()
                    .id(login_passweordInput)
                    .margin(bottom = 20.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .fontSize(14.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .toAttrs {
                        attr("placeholder", "Password")
                    }
            )


            Button(
               attrs = Modifier
                   .margin(bottom = 24.px)
                   .width(350.px)
                   .height(54.px)
                   .backgroundColor(Theme.PrimaryLight.rgb)
                   .color(Colors.White)
                   .fontFamily(FONT_FAMILY)
                   .fontSize(14.px)
                   .fontWeight(FontWeight.Medium)
                   .borderRadius(r = 4.px)
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
                   .cursor(Cursor.Pointer)
                   .onClick {
                       scope.launch {
                           val username: String =
                               (document.getElementById(login_userNameInput) as HTMLInputElement).value
                           val password: String =
                               (document.getElementById(login_passweordInput) as HTMLInputElement).value
                           if (username.isNotEmpty() && password.isNotEmpty()) {
                               val user: UserWithoutPassword? = requestUserCheck(
                                   User(
                                       username = username,
                                       password = password
                                   )
                               )
                               if (user !=null){
                                   rememberLoggedIn(remember = true,user=user)
                                   context.router.navigateTo(Screen.AdminHome.route)
                               }else{
                                   errorText = "user does not exist."
                                   delay(3000)
                                   errorText = " "
                               }
                           } else {
                               errorText = "input fields are empty."
                               delay(3000)
                               errorText = " "
                           }
                       }
                   }
                   .toAttrs()
            ){
                SpanText(text = "LogIn")
            }

            SpanText(
                modifier = Modifier
                    .width(250.px)
                    .color(Colors.Red)
                    .fontFamily(FONT_FAMILY)
                    .textAlign(TextAlign.Center),
                text = errorText
            )

        }
    }


}






private fun rememberLoggedIn(
    remember:Boolean,
    user:UserWithoutPassword?=null
){
    localStorage[REMEMBERED_STORAGE_VALUE] = remember.toString()

    if (user !=null){
        localStorage[USER_ID_STORAGE_VALUE] = user._id
        localStorage[USER_NAME_STORAGE_VALUE] = user.username
    }

}