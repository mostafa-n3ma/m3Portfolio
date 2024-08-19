package org.example.m3portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
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
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.modifiers.rotate
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.example.m3portfolio.AppStrings
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Ids
import org.example.m3portfolio.Res
import org.example.m3portfolio.components.MessagePopup
import org.example.m3portfolio.getLangString
import org.example.m3portfolio.models.ApiInfoResponse
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.sections.FooterSection
import org.example.m3portfolio.util.BigObjectUiState
import org.example.m3portfolio.util.requestInfoData
import org.example.m3portfolio.util.splitLanguages
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Iframe
import org.jetbrains.compose.web.dom.Th
import org.w3c.dom.Attr
import org.w3c.dom.get


@Page(routeOverride = "/about_me")
@Composable
fun AboutMePage() {
    val breakpoint = rememberBreakpoint()
    val bigObject = remember { mutableStateOf(BigObjectUiState()) }
    val context = rememberPageContext()


    val displayLanguage: MutableState<Constants.Languages> = remember(
        window.localStorage[Constants.LANGUAGE_STORAGE_VALUE]
    ) {
        mutableStateOf(
            Constants.Languages.valueOf(
                localStorage[Constants.LANGUAGE_STORAGE_VALUE] ?: "EN"
            )
        )
    }

    if (bigObject.value.messagePopup.isNotEmpty()) {
        MessagePopup(
            message = bigObject.value.messagePopup,
            onDialogDismiss = {
                bigObject.value = bigObject.value.copy(messagePopup = "")
            }
        )
    }


    LaunchedEffect(Unit) {
        requestInfoData(
            onSuccess = { response ->
                when (response) {
                    is ApiInfoResponse.Error -> {
                        println("from AboutMe Page :requestInfoData.response.error = ${response.message}")
                    }

                    ApiInfoResponse.Idle -> {
                        println("from AboutMe Page :requestInfoData.response.Ideal ")

                    }

                    is ApiInfoResponse.Success -> {
                        println("from AboutMe Page :requestInfoData.response.success = ${response.data}")
                        bigObject.value = bigObject.value.copy(
                            infoObject = response.data
                        ).splitLanguages(displayLanguage.value)
                        val slipter = bigObject.value.copy(
                            infoObject = response.data
                        ).splitLanguages(displayLanguage.value)
                        println(
                            "from the success result about me page slipter.about = ${
                                slipter.splitLanguages(
                                    displayLanguage.value
                                ).infoObject.about
                            }"
                        )

                    }
                }
            },
            onError = {

            }
        )
    }



    LaunchedEffect(breakpoint) {
        document.getElementById(Ids.aboutMeDiv)?.innerHTML =
            bigObject.value.infoObject.about
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .backgroundColor(Colors.White)
    ) {
        AboutMeHeader(context, breakpoint, displayLanguage)
        if (breakpoint > Breakpoint.MD) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.px, leftRight = 50.px)
            ) {
                ImageSection(
                    modifier = Modifier.align(Alignment.CenterStart),
                    breakpoint = breakpoint,
                    bigObject = bigObject
                )

                Box(
                    modifier = Modifier
                        .width(50.percent)
                        .height(400.px)
                        .align(Alignment.CenterEnd)

                ) {
                    Div(
                        attrs = Modifier
                            .id(Ids.aboutMeDiv)
                            .fontSize(20.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Normal)
                            .maxHeight(500.px)
                            .resize(Resize.None)
                            .overflow(Overflow.Auto)
                            .scrollBehavior(ScrollBehavior.Unset)
                            .toAttrs {
                                attr("overflow", "scroll")
                                if (displayLanguage.value == Constants.Languages.AR) {
                                    attr("lang", "ar")
                                    attr("dir", "rtl")
                                }
                            }
                    ).let {
                        document.getElementById(Ids.aboutMeDiv)?.innerHTML =
                            bigObject.value.splitLanguages(displayLanguage.value).infoObject.about
                    }
                }


            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.px, leftRight = 30.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageSection(
                    breakpoint = breakpoint,
                    bigObject = bigObject
                )

                Box(
                    modifier = Modifier
                        .width(100.percent)
//                        .height(400.px)
                ) {
                    Div(
                        attrs = Modifier
                            .id(Ids.aboutMeDiv)
                            .fontSize(20.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Normal)
//                            .maxHeight(500.px)
                            .resize(Resize.None)
                            .overflow(Overflow.Auto)
                            .scrollBehavior(ScrollBehavior.Unset)
                            .toAttrs {
                                attr("overflow", "scroll")
                                if (displayLanguage.value == Constants.Languages.AR) {
                                    attr("lang", "ar")
                                    attr("dir", "rtl")
                                }
                            }
                    ).let {
//                        document.getElementById(Ids.aboutMeDiv)?.innerHTML =
//                            bigObject.value.splitLanguages(displayLanguage.value).infoObject.about

                        document.getElementById(Ids.aboutMeDiv)?.innerHTML =
                            bigObject.value.infoObject.about
                    }
                }

            }
        }


        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = 40.px)
                .padding(leftRight = 30.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(20.px)
                .fontWeight(FontWeight.Normal)
                .attrsModifier {
                    if (displayLanguage.value == Constants.Languages.AR) {
                        attr("lang", "ar")
                        attr("dir", "rtl")
                    }
                },
            text = getLangString(AppStrings.always_excited, displayLanguage.value)
        )

        SpanText(
            modifier = Modifier.fillMaxWidth()
                .fontFamily(FONT_FAMILY)
                .margin(topBottom = 20.px)
                .padding(leftRight = 30.px)
                .fontSize(20.px)
                .fontWeight(FontWeight.Normal)
                .attrsModifier {
                    if (displayLanguage.value == Constants.Languages.AR) {
                        attr("lang", "ar")
                        attr("dir", "rtl")
                    }
                },
            text = getLangString(AppStrings.contactMe, displayLanguage.value)
        )


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.px)
        ) {
            Row(
                modifier = Modifier
                    .backgroundColor(Theme.Them_bk_light.rgb)
                    .padding(10.px)
                    .borderRadius(r = 5.px)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.px)
            ) {

                Image(
                    modifier = Modifier
                        .size(
                            if (breakpoint > Breakpoint.MD) 64.px
                            else 32.px
                        )
                    ,
                    src = Res.Icon.gmail
                )


                Link(
                    modifier = Modifier
                        .fontSize(20.px)
                        .fontFamily(FONT_FAMILY)
                        .fontWeight(FontWeight.Bold),
                    path = "mailto:mostafa.n3ma@gmail.com?subject=Portfolio Inquiry&body=Hello, I would like to inquire about...",
                    text = "mostafa.n3ma@gmail.com"
                )
            }


            Column(
                modifier = Modifier
                    .backgroundColor(Theme.Them_bk_light.rgb)
                    .padding(5.px)
                    .borderRadius(r = 5.px)
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(
                                if (breakpoint > Breakpoint.MD) 64.px
                                else 32.px
                            )
                        ,
                        src = Res.Icon.Smartphone
                    )
                    SpanText(
                        modifier = Modifier.fontSize(20.px),
                        text = "+9647825313141"
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(
                                if (breakpoint > Breakpoint.MD) 300.px
                                else 150.px
                            )
                        ,
                        src = "https://github.com/mostafa-n3ma/my-gallery-repository/blob/main/m4.png?raw=true"
                    )
                    Image(
                        modifier = Modifier
                            .size(
                                if (breakpoint > Breakpoint.MD) 300.px
                                else 150.px
                            )
                        ,
                        src = "https://github.com/mostafa-n3ma/my-gallery-repository/blob/main/m5.png?raw=true"
                    )
                }
            }



            Row(
                modifier = Modifier
                    .backgroundColor(Theme.Them_bk_light.rgb)
                    .padding(5.px)
                    .borderRadius(r = 5.px)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.px)
            ) {

                Image(
                    modifier = Modifier
                        .size(
                            if (breakpoint > Breakpoint.MD) 64.px
                            else 32.px
                        )
                    ,
                    src = Res.Icon.location
                )


                SpanText(
                    modifier = Modifier.fontSize(20.px)
                        .fontFamily(FONT_FAMILY)
                        .fontWeight(FontWeight.Bold)
                        .color(Theme.PrimaryLight.rgb)
                    ,
                    text = "Iraq,Wasit,Kut 52001"
                )
            }


            Row(
                modifier = Modifier
                    .backgroundColor(Theme.Them_bk_light.rgb)
                    .padding(5.px)
                    .borderRadius(r = 5.px)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.px)
            ) {

                Image(
                    modifier = Modifier
                        .size(
                            if (breakpoint > Breakpoint.MD) 64.px
                            else 32.px
                        ),
                    src = Res.Icon.linkedin
                )

                SpanText(
                    modifier = Modifier
                        .fontSize(20.px)
                        .color(Theme.PrimaryLight.rgb)
                        .onClick {
                            context.router.navigateTo("https://www.linkedin.com/in/mostafan3ma/")
                        }
                        .cursor(Cursor.Pointer),
                    text = "www.linkedin.com/in/mostafan3ma"
                )
            }

            Row(
                modifier = Modifier
                    .backgroundColor(Theme.Them_bk_light.rgb)
                    .padding(5.px)
                    .borderRadius(r = 5.px)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.px)
            ) {

                Image(
                    modifier = Modifier
                        .size(
                            if (breakpoint > Breakpoint.MD) 64.px
                            else 32.px
                        ),
                    src = Res.Image.Github
                )

                SpanText(
                    modifier = Modifier
                        .fontSize(20.px)
                        .color(Theme.PrimaryLight.rgb)
                        .onClick {
                            context.router.navigateTo("https://github.com/mostafa-n3ma")
                        }
                        .cursor(Cursor.Pointer),
                    text = "https://github.com/mostafa-n3ma"
                )
            }



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        if (breakpoint > Breakpoint.MD) 1000.px
                        else 500.px
                    )
                    .backgroundColor(Theme.Them_bk_light.rgb)
                    .padding(10.px),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.px)
            ) {

                SpanText(
                    modifier = Modifier
                        .fontSize(20.px)
                        .color(Theme.PrimaryLight.rgb),
                    text = getLangString(AppStrings.for_more_details, displayLanguage.value)
                )

                Iframe(
                    attrs = Modifier
                        .fillMaxSize()
                        .padding(20.px)
                        .toAttrs {
                            attr(
                                "src",
                                bigObject.value.infoObject.resumeLink
                            )
                            attr("allow", "autoplay")
                        }
                )


            }


        }









        Box(
            modifier = Modifier.fillMaxWidth().padding(topBottom = 10.px).backgroundColor(Theme.Them_bk_light.rgb)
        )
        FooterSection(breakpoint = breakpoint, context = context, displayLanguage = displayLanguage)

    }


}

@Composable
fun ImageSection(
    modifier: Modifier = Modifier,
    breakpoint: Breakpoint,
    bigObject: MutableState<BigObjectUiState>,

    ) {
    val scope = rememberCoroutineScope()
    scope.launch {

    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .width(
                    if (breakpoint > Breakpoint.MD) 444.px
                    else 322.px
                )
                .height(
                    if (breakpoint > Breakpoint.MD) 581.px
                    else 481.px
                )
                .zIndex(1),
            src = Res.Image.ellipse
        )

        Image(
            modifier = Modifier
                .width(
                    if (breakpoint > Breakpoint.MD) 400.px
                    else 300.px
                )
                .height(
                    if (breakpoint > Breakpoint.MD) 566.px
                    else 466.px
                )
                .zIndex(3),
            src = bigObject.value.infoObject.extra
//            src = "https://github.com/mostafa-n3ma/my-gallery-repository/blob/main/tea_nobk.png?raw=true"
        )


        Box(
            modifier = Modifier
                .backgroundColor(Colors.Transparent)
                .borderRadius(r = 10.px)
                .margin(left = 24.px, top = 10.px)
                .border(
                    width = 3.px,
                    style = LineStyle.Solid,
                    color = Colors.Black
                )
                .width(
                    if (breakpoint > Breakpoint.MD) 367.px
                    else 267.px
                )
                .height(
                    if (breakpoint > Breakpoint.MD) 475.px
                    else 375.px
                )
                .rotate((-5).deg)
                .zIndex(2)
        ) {

        }


    }
}

@Composable
fun AboutMeHeader(
    context: PageContext,
    breakpoint: Breakpoint,
    displayLanguage: MutableState<Constants.Languages>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.vh)
            .padding(leftRight = 20.px)
            .backgroundColor(Theme.Them_bk_light.rgb),
        horizontalArrangement = Arrangement.spacedBy(20.px),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .size(32.px)
                .cursor(Cursor.Pointer)
                .onClick {
                    context.router.navigateTo(Screen.Home.route)
                },
            src = Res.Image.back
        )
        SpanText(
            modifier = Modifier
                .fontSize(if (breakpoint > Breakpoint.MD) 32.px else 24.px)
                .fontFamily(FONT_FAMILY)
                .fontWeight(FontWeight.Bold),
            text = getLangString(AppStrings.MostafaN3ma, displayLanguage.value)
        )
    }
}
