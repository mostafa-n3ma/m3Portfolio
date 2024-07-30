package org.example.m3portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
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
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Ids
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.Res
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.pages.BigObjectUiState
import org.example.m3portfolio.styles.GithubBtnStyle
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Div

@Composable
fun MainSection(
    breakpoint: Breakpoint,
    bigObject: BigObjectUiState,
    context: PageContext,
) {

    val colorMode by ColorMode.currentState


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .maxWidth(100.vw)
            .id(Constants.MAIN_SECTION)
//            .height(100.vh)
            .padding(top = if (breakpoint <= Breakpoint.MD) 0.px else Measurements.HEADER_HEIGHT.px)
//            .backgroundColor(Theme.PrimaryLight.rgb),
            .backgroundImage(
                linearGradient(
                    dir =  LinearGradient.Direction.ToRight,
                    from = if (colorMode.isLight) Theme.PrimaryLight.rgb
                    else Theme.PrimaryLight.rgb
                    ,
                    to = if (colorMode.isLight) Theme.gradient_light_to.rgb
                    else Colors.Black
                    ,
                )
            )
    ) {

        when(breakpoint){
            Breakpoint.LG -> {
                LaunchedEffect(breakpoint){
                    if (breakpoint == Breakpoint.LG){
                        document.getElementById(Ids.mainSectionBioDiv)?.innerHTML = bigObject.infoObject.bio
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(54.px)
                ) {
                    Image(
                        src = Res.Image.my_image_jpg,
                        modifier = Modifier
                            .width(400.px)
                            .height(530.px)
                            .align(Alignment.CenterStart )
                            .borderRadius(r = 4.px)
                    )

                    Box(
                        modifier = Modifier
                            .width(50.percent)
                            .height(400.px)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.TopEnd

                    ){
                        Div(
                            attrs = Modifier
                                .id(Ids.mainSectionBioDiv)
                                .fontSize(24.px)
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .maxHeight(400.px)
                                .resize(Resize.None)
                                .color(Colors.White)
                                .overflow(Overflow.Auto)
                                .scrollBehavior(ScrollBehavior.Unset)
                                .toAttrs{
                                    attr("overflow","scroll")
                                }
                        ).let {
                            document.getElementById(Ids.mainSectionBioDiv)?.innerHTML = bigObject.infoObject.bio
//
                        }
                    }

                    Row (
                        modifier = Modifier.align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.spacedBy(20.px, Alignment.CenterHorizontally)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.px, Alignment.CenterHorizontally)
                        ){
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.android_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.android_studio_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.compos_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.java_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.kotlin_ic
                            )
                        }
                        Row(
                            modifier = GithubBtnStyle.toModifier()
                                .width(250.px)
                                .height(54.px)
                                .backgroundColor(
                                    if (colorMode.isLight) Theme.Them_bk_light.rgb
                                    else Theme.PrimaryLight.rgb
                                )
                                .borderRadius(r = 5.px)
                                .onClick {
                                    context.router.navigateTo(bigObject.infoObject.github)
                                }
                                .noBorder(),
                            horizontalArrangement = Arrangement.spacedBy(5.px)
                        ){
                            Image(
                                modifier = Modifier
                                    .size(45.px)
                                    .margin(left = 5.px)
                                    .align(Alignment.CenterVertically),
                                src = Res.Image.Github
                            )
                            SpanText(
                                modifier = Modifier
                                    .fontFamily(Constants.FONT_FAMILY)
                                    .fontWeight(FontWeight.Bold)
                                    .fontSize(24.px)
                                    .align(Alignment.CenterVertically)
                                ,
                                text = "Github Account"
                            )
                        }
                    }




                }
            }
            Breakpoint.XL -> {
                LaunchedEffect(breakpoint){
                    if (breakpoint == Breakpoint.XL){
                        document.getElementById(Ids.mainSectionBioDiv)?.innerHTML = bigObject.infoObject.bio
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(54.px)
                ) {
                    Image(
                        src = Res.Image.my_image_jpg,
                        modifier = Modifier
                            .width(400.px)
                            .height(530.px)
                            .align(Alignment.TopStart )
                            .borderRadius(r = 4.px)
                    )

                    Box(
                        modifier = Modifier
                            .width(60.percent)
                            .height(450.px)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.TopEnd

                    ){
                        Div(
                            attrs = Modifier
                                .id(Ids.mainSectionBioDiv)
                                .fontSize(24.px)
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .maxHeight(450.px)
                                .resize(Resize.None)
                                .color(Colors.White)
                                .overflow(Overflow.Auto)
                                .scrollBehavior(ScrollBehavior.Smooth)
                                .toAttrs{
                                    attr("overflow","scroll")
                                }
                        ).let {
                            document.getElementById(Ids.mainSectionBioDiv)?.innerHTML = bigObject.infoObject.bio
//
                        }
                    }

                    Row (
                        modifier = Modifier.align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.spacedBy(20.px, Alignment.CenterHorizontally)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.px, Alignment.CenterHorizontally)
                        ){
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.android_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.android_studio_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.compos_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.java_ic
                            )
                            Image(
                                modifier = Modifier.size(50.px),
                                src = Res.Image.kotlin_ic
                            )
                        }
                        Row(
                            modifier = GithubBtnStyle.toModifier()
                                .width(250.px)
                                .height(54.px)
                                .backgroundColor(
                                    if (colorMode.isLight) Theme.Them_bk_light.rgb
                                    else Theme.PrimaryLight.rgb
                                )
                                .borderRadius(r = 5.px)
                                .onClick {
                                    context.router.navigateTo(bigObject.infoObject.github)
                                }
                                .noBorder(),
                            horizontalArrangement = Arrangement.spacedBy(5.px)
                        ){
                            Image(
                                modifier = Modifier
                                    .size(45.px)
                                    .margin(left = 5.px)
                                    .align(Alignment.CenterVertically),
                                src = Res.Image.Github
                            )
                            SpanText(
                                modifier = Modifier
                                    .fontFamily(Constants.FONT_FAMILY)
                                    .fontWeight(FontWeight.Bold)
                                    .fontSize(24.px)
                                    .align(Alignment.CenterVertically)
                                ,
                                text = "Github Account"
                            )
                        }
                    }


                }

            }
            else -> {
                LaunchedEffect(breakpoint){
                    if (breakpoint <= Breakpoint.MD){
                        document.getElementById(Ids.mainSectionBioDiv)?.innerHTML = bigObject.infoObject.bio
                    }
                }
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 54.px),
                    verticalArrangement = Arrangement.spacedBy(20.px)
                ) {
                    Image(
                        src = Res.Image.my_image_jpg,
                        modifier = Modifier
                            .width(200.px)
                            .height(250.px)
                            .align(Alignment.CenterHorizontally )
                            .borderRadius(r = 4.px)
                    )

                    Box(
                        modifier = Modifier
                            .width(90.percent)
                            .align(Alignment.CenterHorizontally),
                    ){
                        Div(
                            attrs = Modifier
                                .id(Ids.mainSectionBioDiv)
                                .fontSize(16.px)
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .resize(Resize.None)
                                .color(Colors.White)
                                .toAttrs()
                        ).let {
                            document.getElementById(Ids.mainSectionBioDiv)?.innerHTML = bigObject.infoObject.bio
                        }
                    }


                    Row(
                        modifier = GithubBtnStyle.toModifier()
                            .width(200.px)
                            .height(54.px)
                            .margin(bottom = 20.px)
                            .backgroundColor(
                                if (colorMode.isLight) Theme.Them_bk_light.rgb
                                else Theme.PrimaryLight.rgb
                            )
                            .borderRadius(r = 5.px)
                            .onClick {
                                context.router.navigateTo(bigObject.infoObject.github)
                            }
                            .align(Alignment.CenterHorizontally)
                            .noBorder(),
                        horizontalArrangement = Arrangement.spacedBy(5.px),
                    ){
                        Image(
                            modifier = Modifier
                                .size(45.px)
                                .margin(left = 5.px)
                                .align(Alignment.CenterVertically),
                            src = Res.Image.Github
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontWeight(FontWeight.Bold)
                                .fontSize(18.px)
                                .align(Alignment.CenterVertically)
                            ,
                            text = "Github Account"
                        )
                    }
                }
            }
        }


    }
}
