package org.example.m3portfolio.sections.project_sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import kotlinx.browser.document
import org.example.m3portfolio.AppStrings
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Constants.LANGUAGES_SPLITTER_CODE
import org.example.m3portfolio.Ids.projectPreviewDescriptionDiv
import org.example.m3portfolio.getLangString
import org.example.m3portfolio.models.Project
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun ProjectDescriptionSection(
    breakpoint: Breakpoint,
    project: Project,
    context: PageContext,
    displayLanguage: MutableState<Constants.Languages>,
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .id(Constants.PROJECT_DESCRIPTION_SECTION)
            .margin(topBottom = 20.px)
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.px)
        ){
            SpanText(
                modifier = Modifier
                    .fontSize(32.px)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Bold)
                ,
                text = getLangString(AppStrings.projectDescriptionHeader,displayLanguage.value)
            )
                Div(
                    attrs = Modifier
                        .id(projectPreviewDescriptionDiv)
                        .fontSize(
                            when(breakpoint){
                                Breakpoint.ZERO -> 8.px
                                Breakpoint.SM -> 10.px
                                Breakpoint.MD -> 12.px
                                Breakpoint.LG -> 16.px
                                Breakpoint.XL -> 18.px
                            }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .fontFamily(FONT_FAMILY)
                        .fontWeight(FontWeight.Normal)
                        .toAttrs {
                            if (displayLanguage.value == Constants.Languages.AR) {
                                attr("lang", "ar")
                                attr("dir", "rtl")
                            }
                        }
                ).let {
                    when (displayLanguage.value) {
                        Constants.Languages.EN -> document.getElementById(projectPreviewDescriptionDiv)?.innerHTML =
                            project.description.split(LANGUAGES_SPLITTER_CODE)[0]

                        Constants.Languages.AR -> document.getElementById(projectPreviewDescriptionDiv)?.innerHTML =
                            project.description.split(LANGUAGES_SPLITTER_CODE)[1]
                    }
                }

        }
    }






}