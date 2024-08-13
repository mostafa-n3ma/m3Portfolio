package org.example.m3portfolio.sections.project_sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.AspectRatio
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.m3portfolio.AppStrings
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Constants.PROJECT_MAIN_SECTION
import org.example.m3portfolio.Res
import org.example.m3portfolio.components.LoadingIndicator
import org.example.m3portfolio.getLangString
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.styles.GithubBtnStyle
import org.example.m3portfolio.util.BigObjectUiState
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

@Composable
fun MainProjectSection(
    breakpoint: Breakpoint,
    bigObject: BigObjectUiState,
    context: PageContext,
    displayLanguage: MutableState<Constants.Languages>,
) {
    val colorMode by ColorMode.currentState
    val langCode = when (displayLanguage.value) {
        Constants.Languages.EN -> 0
        Constants.Languages.AR -> 1
    }

    if (bigObject.projectsList.first() == Project()) {
        //loading
        LoadingIndicator()
    } else {
        Box(
            modifier = Modifier.fillMaxWidth().id(PROJECT_MAIN_SECTION)
        ) {
            if (breakpoint > Breakpoint.MD) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.vh)
                        .transition(
                            Transition.of(
                                property = TransitionProperty.All.toString(),
                                duration = 500.ms
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(300.px)
                                .margin(leftRight = 20.px)
                                .borderRadius(r = 15.px)
                                .styleModifier {
                                    property("object-fit", "contain")
                                },
                            src = bigObject.projectsList.first().mainImageLink
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SpanText(
                                modifier = Modifier
                                    .fontFamily(FONT_FAMILY)
                                    .fontSize(
                                        when (breakpoint) {
                                            Breakpoint.XL -> 32.px
                                            Breakpoint.LG -> 28.px
                                            else -> 28.px
                                        }
                                    )
                                    .fontWeight(FontWeight.Bold)
                                    .color(
                                        if (colorMode.isLight) Theme.PrimaryLight.rgb
                                        else Colors.White
                                    ),
                                text = bigObject.projectsList.first().title,

                                )

                            SpanText(
                                modifier = Modifier
                                    .fontFamily(Constants.FONT_FAMILY)
                                    .fontSize(24.px)
                                    .fontWeight(FontWeight.Normal)
                                    .color(
                                        if (colorMode.isLight) Theme.HalfBlack.rgb
                                        else Theme.HalfWhite.rgb
                                    ),
                                text = bigObject.projectsList.first().date,
                            )

                            Row(
                                modifier = GithubBtnStyle.toModifier()
//                                .width(300.px)
                                    .height(54.px)
                                    .padding(10.px)
                                    .backgroundColor(
                                        if (colorMode.isLight) Theme.Them_bk_light.rgb
                                        else Theme.PrimaryLight.rgb
                                    )
                                    .borderRadius(r = 5.px)
                                    .onClick {
                                        context.router.navigateTo(bigObject.projectsList.first().repoLink)
                                    }
                                    .noBorder(),
                                horizontalArrangement = Arrangement.spacedBy(
                                    5.px,
                                    Alignment.CenterHorizontally
                                )
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(45.px)
                                        .margin(left = 5.px)
                                        .align(Alignment.CenterVertically),
                                    src = Res.Image.Github
                                )
                                SpanText(
                                    modifier = Modifier
                                        .fontFamily(FONT_FAMILY)
                                        .fontWeight(FontWeight.Bold)
                                        .fontSize(22.px)
                                        .color(
                                            if (colorMode.isLight) Theme.PrimaryLight.rgb
                                            else Colors.White
                                        )
                                        .align(Alignment.CenterVertically),
                                    text = "Check the Code"
                                )
                            }


                        }
                    }


                    SpanText(
                        modifier = Modifier
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontSize(
                                when (breakpoint) {
                                    Breakpoint.LG -> 16.px
                                    Breakpoint.XL -> 20.px
                                    else -> 16.px
                                }
                            )
                            .maxWidth(
                                when (breakpoint) {
                                    Breakpoint.LG -> 35.percent
                                    Breakpoint.XL -> 50.percent
                                    else -> 50.percent
                                }
                            )
                            .fontWeight(FontWeight.Normal)
                            .color(
                                if (colorMode.isLight) Theme.HalfBlack.rgb
                                else Theme.HalfWhite.rgb
                            )
                            .align(Alignment.CenterEnd)
                            .attrsModifier {
                                attr("overflow", "scroll")
                                if (displayLanguage.value == org.example.m3portfolio.Constants.Languages.AR) {
                                    attr("lang", "ar")
                                    attr("dir", "rtl")
                                }
                            },
                        text = bigObject.projectsList.first().subTitle,
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth().margin(top = 20.px)
                            .align(Alignment.BottomCenter)
                    ) {
                        SpanText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fontFamily(FONT_FAMILY)
                                .fontSize(20.px)
                                .fontWeight(FontWeight.Bold)
                                .attrsModifier {
                                    attr("overflow", "scroll")
                                    if (displayLanguage.value == org.example.m3portfolio.Constants.Languages.AR) {
                                        attr("lang", "ar")
                                        attr("dir", "rtl")
                                    }
                                },
                            text = getLangString(
                                AppStrings.projectTechStack,
                                displayLanguage.value
                            ),
                        )

                        SpanText(
                            modifier = Modifier
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontSize(18.px)
                                .fontWeight(FontWeight.Normal),
                            text = bigObject.projectsList.first().techStack,
                        )
                    }


                }

            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Image(
                        modifier = Modifier
                            .size(200.px)
                            .borderRadius(r = 15.px)
                            .styleModifier {
                                property("object-fit", "contain")
                            },
                        src = bigObject.projectsList.first().mainImageLink
                    )
                    SpanText(
                        modifier = Modifier
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontSize(24.px)
                            .fontWeight(FontWeight.Bold)
                            .color(
                                if (colorMode.isLight) Theme.PrimaryLight.rgb
                                else Colors.White
                            ),
                        text = bigObject.projectsList.first().title,

                        )

                    SpanText(
                        modifier = Modifier
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontSize(18.px)
                            .fontWeight(FontWeight.Normal)
                            .color(
                                if (colorMode.isLight) Theme.HalfBlack.rgb
                                else Theme.HalfWhite.rgb
                            ),
                        text = bigObject.projectsList.first().date,
                    )

                    SpanText(
                        modifier = Modifier
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontSize(12.px)
                            .fontWeight(FontWeight.Normal)
                            .color(
                                if (colorMode.isLight) Theme.HalfBlack.rgb
                                else Theme.HalfWhite.rgb
                            )
                            .attrsModifier {
                                attr("overflow", "scroll")
                                if (displayLanguage.value == org.example.m3portfolio.Constants.Languages.AR) {
                                    attr("lang", "ar")
                                    attr("dir", "rtl")
                                }
                            },
                        text = bigObject.projectsList.first().subTitle,
                    )

                    Row(
                        modifier = GithubBtnStyle.toModifier()
//                                .width(300.px)
                            .height(54.px)
                            .padding(10.px)
                            .margin(top = 20.px)
                            .backgroundColor(
                                if (colorMode.isLight) Theme.Them_bk_light.rgb
                                else Theme.PrimaryLight.rgb
                            )
                            .borderRadius(r = 5.px)
                            .onClick {
                                context.router.navigateTo(bigObject.projectsList.first().repoLink)
                            }
                            .noBorder(),
                        horizontalArrangement = Arrangement.spacedBy(
                            5.px,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        Image(
                            modifier = Modifier
                                .size(45.px)
                                .margin(left = 5.px)
                                .align(Alignment.CenterVertically),
                            src = Res.Image.Github
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Bold)
                                .fontSize(18.px)
                                .color(
                                    if (colorMode.isLight) Theme.PrimaryLight.rgb
                                    else Colors.White
                                )
                                .align(Alignment.CenterVertically),
                            text = "Check the Code"
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().margin(top = 20.px)
                    ) {
                        SpanText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontSize(16.px)
                                .fontWeight(FontWeight.Bold)
                                .attrsModifier {
                                    attr("overflow", "scroll")
                                    if (displayLanguage.value == org.example.m3portfolio.Constants.Languages.AR) {
                                        attr("lang", "ar")
                                        attr("dir", "rtl")
                                    }
                                },
                            text = getLangString(
                                AppStrings.projectTechStack,
                                displayLanguage.value
                            ),
                        )

                        SpanText(
                            modifier = Modifier
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontSize(12.px)
                                .fontWeight(FontWeight.Normal),
                            text = bigObject.projectsList.first().techStack,
                        )
                    }


                }
            }
        }


    }


}