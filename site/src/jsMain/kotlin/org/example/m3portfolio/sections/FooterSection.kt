package org.example.m3portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.pages.BigObjectUiState
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Composable
fun FooterSection(
    breakpoint: Breakpoint,
    bigObject: MutableState<BigObjectUiState>,
    context:PageContext
) {
   Row(
       modifier = Modifier.fillMaxWidth().height(100.px)
           .backgroundColor(Theme.Them_bk_light.rgb),
       verticalAlignment = Alignment.CenterVertically
   ) {
       SpanText(
           modifier = Modifier
               .maxWidth(60.percent).margin(left = 20.px, right = 40.px)
               .color(Colors.Black)
           ,
           text = "Mostafa N3ma (V 1.0) - Copyright @ 2024 Personal Website"
       )

       if (breakpoint>=Breakpoint.MD){
           Row(
               horizontalArrangement = Arrangement.spacedBy(10.px)
           ){
               if (bigObject.value.websitesList.isNotEmpty()){
                   bigObject.value.websitesList.forEach {websiteItem->
                       Image(
                           src = websiteItem.icon,
                           modifier = Modifier.size(32.px)
                               .borderRadius(r = 15.px)
                               .onClick {
                                   context.router.navigateTo(websiteItem.link)
                               }
                       )
                   }
               }else{
//                   bigObject.value = bigObject.value.copy(messagePopup = "websites list is empty: ${bigObject.value.websitesList}")
               }
           }
       }




   }
}