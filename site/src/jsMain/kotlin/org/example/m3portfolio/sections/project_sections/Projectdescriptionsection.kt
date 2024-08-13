package org.example.m3portfolio.sections.project_sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
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
import org.example.m3portfolio.util.BigObjectUiState
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun ProjectDescriptionSection(
    breakpoint: Breakpoint,
    bigObject: BigObjectUiState,
    context: PageContext,
    displayLanguage: MutableState<Constants.Languages>,
) {
    val langCode = when (displayLanguage.value) {
        Constants.Languages.EN -> 0
        Constants.Languages.AR -> 1
    }

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
                    .fillMaxWidth()
                    .fontSize(32.px)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Bold)
                    .attrsModifier {
                        attr("overflow", "scroll")
                        if (displayLanguage.value == org.example.m3portfolio.Constants.Languages.AR) {
                            attr("lang", "ar")
                            attr("dir", "rtl")
                        }
                    }
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
                         document.getElementById(projectPreviewDescriptionDiv)?.innerHTML =
                            bigObject.projectsList.first().description.split(LANGUAGES_SPLITTER_CODE)
                                .getOrElse(langCode) {
                                    bigObject.projectsList.first().description
                                }
                }
        }
    }






}