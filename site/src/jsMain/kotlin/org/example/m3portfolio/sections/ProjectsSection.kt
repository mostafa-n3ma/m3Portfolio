package org.example.m3portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
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
import org.example.m3portfolio.components.LoadingIndicator
import org.example.m3portfolio.getLangString
import org.example.m3portfolio.models.Project
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.navigation.Screen
import org.example.m3portfolio.styles.ItemStyle
import org.example.m3portfolio.util.BigObjectUiState
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw


@Composable
fun ProjectsSection(
    breakpoint: Breakpoint,
    bigObject: BigObjectUiState,
    context: PageContext,
    displayLanguage: MutableState<Constants.Languages>,
) {
    val colorMode by ColorMode.currentState
    var currentList by remember {
        mutableStateOf(listOf<Project>())
    }
    println(bigObject.projectsList)
    if (bigObject.projectsList.isNotEmpty()){
        currentList = when(breakpoint){

            Breakpoint.MD -> bigObject.projectsList.subList(0,3)
            Breakpoint.LG -> bigObject.projectsList.subList(0,5)
            Breakpoint.XL -> bigObject.projectsList.subList(0,7)
            else-> bigObject.projectsList.subList(0,3)
        }
    }

    if (bigObject.projectsList.isEmpty()){
        LoadingIndicator()
    }else{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(100.vw)
                .id(Constants.PROJECTS_SECTION)
//            .height(100.vh)
                .padding(top = if (breakpoint <= Breakpoint.MD) 0.px else Measurements.HEADER_HEIGHT.px)
                .backgroundColor(
                    if (colorMode.isLight) Theme.Them_bk_light.rgb
                    else Theme.Them_bk_dark.rgb
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
                horizontalArrangement = Arrangement.SpaceBetween,
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
                        .fontFamily(FONT_FAMILY)
                        .fontWeight(FontWeight.Bold)
                        .color(
                            if (colorMode.isLight) Theme.PrimaryLight.rgb
                            else Colors.White
                        ),
                    text = getLangString(AppStrings.projects_i_worked_on,displayLanguage.value)
                )

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
                        .fontFamily(FONT_FAMILY)
                        .fontWeight(FontWeight.Bold)
                        .color(
                            if (colorMode.isLight) Theme.PrimaryLight.rgb
                            else Colors.White
                        )
                        .cursor(Cursor.Pointer)
                        .onClick {
                            currentList = bigObject.projectsList
                        },
                    text = getLangString(AppStrings.see_more,displayLanguage.value)
                )
            }
            SimpleGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                numColumns = numColumns(base = 1, md = 2, lg = 3, xl = 4)
            ) {

                currentList.forEach { projectItem ->
                    Column(
                        modifier = ItemStyle.toModifier()
                            .width(
                                if (breakpoint < Breakpoint.MD) 80.percent
                                else 350.px
                            )
                            .height(500.px)
                            .padding(20.px)
                            .borderRadius(r = 4.px)
                            .margin(topBottom = 30.px, leftRight = 10.px)
                            .backgroundColor(
                                if (colorMode.isLight) Theme.Item_bk_light.rgb
                                else Theme.Item_bk_dark.rgb
                            )
                            .onClick {
                                     context.router.navigateTo(Screen.ProjectPreview.passProjectId(projectItem._id))
                            }


                        ,
                        verticalArrangement = Arrangement.spacedBy(10.px),
                    ) {
                        Image(
                            modifier = Modifier
                                .size(300.px)
                                .align(Alignment.CenterHorizontally),
                            src = projectItem.mainImageLink
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontSize(32.px)
                                .fontWeight(FontWeight.Bold)
                                .color(Theme.PrimaryDark.rgb),
                            text = projectItem.title
                        )


                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fillMaxWidth()
                                .fontSize(18.px)
                                .maxHeight(50.px)
                                .fontWeight(FontWeight.Normal)
                                .color(Theme.PrimaryLight.rgb),
                            text = projectItem.subTitle.take(100)
                        )


                    }
                }


            }
        }
    }


}