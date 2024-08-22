package org.example.m3portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
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
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.m3portfolio.AppStrings
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Measurements
import org.example.m3portfolio.Res
import org.example.m3portfolio.components.LoadingIndicator
import org.example.m3portfolio.getLangString
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.styles.ItemStyle
import org.example.m3portfolio.util.BigObjectUiState
import org.example.m3portfolio.util.noBorder
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw


@Composable
fun CertificatesSection(
    breakpoint: Breakpoint,
    bigObject: BigObjectUiState,
    context: PageContext,
    displayLanguage: MutableState<Constants.Languages>,
) {
    val colorMode by ColorMode.currentState


    if (bigObject.certificatesList.isEmpty()){
        LoadingIndicator()
    }else{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(100.vw)
                .id(Constants.CERTIFICATES_SECTION)
                .padding(top = if (breakpoint <= Breakpoint.MD) 0.px else Measurements.HEADER_HEIGHT.px)
                .backgroundColor(
                    if (colorMode.isLight) Colors.White
                    else Theme.Them_bk_dark_2.rgb
                )
                .transition(
                    Transition.of(
                        property = TransitionProperty.All.toString(),
                        duration = 500.ms
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(leftRight = 40.px).margin(bottom = 20.px),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpanText(
                    modifier = Modifier
                        .fontSize(
                            when (breakpoint) {
                                Breakpoint.XL -> 28.px
                                Breakpoint.LG -> 22.px
                                Breakpoint.MD -> 18.px
                                else -> 16.px
                            }
                        )
                        .fontFamily(Constants.FONT_FAMILY)
                        .fontWeight(FontWeight.Bold)
                        .color(
                            if (colorMode.isLight) Theme.PrimaryLight.rgb
                            else Colors.White
                        ),
                    text = getLangString(AppStrings.certificates_i_hold,displayLanguage.value)
                )

            }

            SimpleGrid(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                numColumns = numColumns(base = 1, xl = 2
                )){
                bigObject.certificatesList.forEach {certificateItem->
                    Box(
                        modifier = ItemStyle.toModifier()
                            .width(90.percent)
                            .height(500.px)
                            .padding(all = 20.px)
                            .margin(20.px)
                            .noBorder()
                            .align(Alignment.CenterHorizontally)
                            .borderRadius(topRight = 100.px, bottomLeft = 100.px)
                            .backgroundColor(Theme.Them_bk_light.rgb)
                            .onClick {
                                context.router.navigateTo(certificateItem.link)
                            }
                    ){
                        Column (
                            modifier = Modifier.align(Alignment.TopStart),
                            verticalArrangement = Arrangement.spacedBy(10.px)
                        ){
                            SpanText(
                                modifier = Modifier
                                    .fontFamily(FONT_FAMILY)
                                    .fontSize(24.px)
                                    .maxWidth(550.px)
                                    .maxHeight(100.px)
                                    .fontWeight(FontWeight.Bold)
                                    .color(Theme.PrimaryLight.rgb)
                                ,
                                text = certificateItem.title
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.px)
                            ) {
                                SpanText(
                                    modifier = Modifier
                                        .fontFamily(FONT_FAMILY)
                                        .fontSize(16.px)
                                        .fontWeight(FontWeight.Normal)
                                        .color(Colors.Black)

                                    ,
                                    text = certificateItem.from
                                )
                                SpanText(
                                    modifier = Modifier
                                        .fontFamily(FONT_FAMILY)
                                        .fontSize(16.px)
                                        .fontWeight(FontWeight.Normal)
                                        .color(Theme.HalfBlack.rgb)
                                    ,
                                    text = certificateItem.date
                                )
                            }

                        }

                        Image(
                            modifier = Modifier.size(50.percent).align(Alignment.CenterStart).margin(left = 30.px),
                            src = certificateItem.thumbnailLink
                        )

                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontSize(14.px)
                                .fontWeight(FontWeight.Normal)
                                .color(Theme.HalfBlack.rgb)
                                .align(Alignment.BottomStart)
                                .margin(left = 30.px, bottom = 50.px)
                            ,
                            text = "Go To Original Link >>>>"
                        )

                        Image(
                            modifier = Modifier
                                .size(
                                    if (breakpoint < Breakpoint.MD) 200.px
                                    else 300.px
                                )
                                .align(Alignment.BottomEnd),
                            src = Res.Image.certificateIcon
                        )



                    }
                }
            }



        }
    }






}