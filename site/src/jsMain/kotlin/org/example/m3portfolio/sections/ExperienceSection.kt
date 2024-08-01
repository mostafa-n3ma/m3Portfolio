package org.example.m3portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.FontWeight
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
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.m3portfolio.AppStrings
import org.example.m3portfolio.Constants
import org.example.m3portfolio.components.LoadingIndicator
import org.example.m3portfolio.getLangString
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.util.BigObjectUiState
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw

@Composable
fun ExperienceSection(
    breakpoint: Breakpoint,
    bigObject: BigObjectUiState,
    displayLanguage: MutableState<Constants.Languages>
) {
    val colorMode by ColorMode.currentState

    if (bigObject.experiencesList.isEmpty()){
        LoadingIndicator()
    }else{
        Column(
            modifier = Modifier
                .id(Constants.EXPERIENCE_SECTION)
                .fillMaxWidth()
                .padding(top = 20.px, leftRight = 40.px)
                .maxWidth(100.vw)
                .backgroundColor(
                    if (colorMode.isLight) Colors.White
                    else Theme.Them_bk_dark_2.rgb
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().margin(bottom = 40.px),
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
                    text = getLangString(AppStrings.experiences,displayLanguage.value)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.px)
                ) {
                    bigObject.experiencesList.forEach { experienceItem ->
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row {
                                Column {
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
                                        text = experienceItem.role
                                    )

                                    SpanText(
                                        modifier = Modifier
                                            .fontFamily(Constants.FONT_FAMILY)
                                            .fontWeight(FontWeight.Normal)
                                            .fontSize(18.px)
                                            .color(Theme.HalfBlack.rgb),
                                        text = "@${experienceItem.location}"
                                    )
                                    SpanText(
                                        modifier = Modifier
                                            .fontFamily(Constants.FONT_FAMILY)
                                            .fontWeight(FontWeight.Normal)
                                            .fontSize(18.px)
                                            .color(Theme.HalfBlack.rgb)
                                        ,
                                        text = experienceItem.duration
                                    )

                                    SpanText(
                                        modifier = Modifier
                                            .fontFamily(Constants.FONT_FAMILY)
                                            .fontWeight(FontWeight.Normal)
                                            .fontSize(18.px)
                                            .color(Theme.HalfBlack.rgb)
                                        ,
                                        text = experienceItem.projects
                                    )

                                }
                                Image(
                                    src = experienceItem.image,
                                    modifier = Modifier.height(100.px).maxWidth(200.px)
                                        .borderRadius(r = 4.px)
                                        .margin(left = 30.px)
                                )
                            }

                            SpanText(
                                modifier = Modifier
                                    .fontFamily(Constants.FONT_FAMILY)
                                    .fontWeight(FontWeight.Normal)
                                    .maxWidth(50.percent)
                                    .fontSize(18.px)
                                    .color(Theme.HalfBlack.rgb),

                                text = experienceItem.description.take(500)
                            )

                        }
                    }
                }


            }


        }
    }

}