package org.example.m3portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translate
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.delay
import org.example.m3portfolio.Constants
import org.example.m3portfolio.Constants.FONT_FAMILY
import org.example.m3portfolio.Res
import org.example.m3portfolio.models.Theme
import org.example.m3portfolio.pages.BigObjectUiState
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import kotlin.random.Random

data class TranslatePortsState(
    //centered alignment
    var port1:Int=0,
    var port2:Int=0,

    //start alignment
    var port3:Int=0,
    var port4:Int=0,

    //end alignment
    var port5:Int=0,
    var port6:Int=0,
)
@Composable
fun SkillsSection(
    breakpoint: Breakpoint,
    bigObject: BigObjectUiState
) {
    val colorMode by ColorMode.currentState
    var translatePorts by remember { mutableStateOf(TranslatePortsState()) }

    randomTranslation(
        port1 = {translatePorts = translatePorts.copy(port1 = it)},
        port2 = {translatePorts = translatePorts.copy(port2 = it)},
        port3 = {translatePorts = translatePorts.copy(port3 = it)},
        port4 = {translatePorts = translatePorts.copy(port4 = it)},
        port5 = {translatePorts = translatePorts.copy(port5 = it)},
        port6 = {translatePorts = translatePorts.copy(port6 = it)},
    )



    Box(
        modifier = Modifier
            .id(Constants.SKILLS_SECTION)
            .fillMaxWidth()
            .maxWidth(100.vw)
            .backgroundColor(
            if (colorMode.isLight) Theme.Them_bk_light.rgb
            else Theme.Them_bk_dark.rgb
        )
    ) {

        translatedIcons(
            translatePorts,
            Modifier
                .fillMaxHeight()
                .width(60.vw)
                .align(Alignment.CenterEnd)
                .zIndex(2)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.px)
                .maxWidth(100.vw)
                .zIndex(8),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(leftRight = 40.px),
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
                    text = "Set Of Skills I Have:"
                )
            }

            ListComposable(
                list = bigObject.infoObject.skills.split(","),
                breakpoint = breakpoint,
                colorMode = colorMode
            )



            Row(
                modifier = Modifier.fillMaxWidth().padding(leftRight = 40.px),
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
                    text = "Programming Languages:"
                )
            }

            ListComposable(
                list = bigObject.infoObject.programLanguages.split(","),
                breakpoint = breakpoint,
                colorMode = colorMode
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(leftRight = 40.px),
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
                    text = "Tools :"
                )
            }

            ListComposable(
                list = bigObject.infoObject.tools.split(","),
                breakpoint = breakpoint,
                colorMode = colorMode
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(leftRight = 40.px),
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
                    text = "FrameWorks :"
                )
            }

            ListComposable(
                list = bigObject.infoObject.frameWorks.split(","),
                breakpoint = breakpoint,
                colorMode = colorMode
            )


        }
    }


}


@Composable
fun ListComposable(list: List<String>, breakpoint: Breakpoint, colorMode: ColorMode) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(leftRight = 40.px, bottom = 20.px)
    ) {
        var itemsCount = 0
        var subList = mutableListOf<String>()
        list.forEachIndexed { index, it ->
            itemsCount += 1
            subList.add(it)
            if (itemsCount == 4) {
                Column(
                    modifier = Modifier.margin(right = 20.px),
                    verticalArrangement = Arrangement.spacedBy(5.px)
                ) {
                    subList.forEach {
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .fontSize(18.px)
                                .color(
                                    if (colorMode.isLight) Theme.PrimaryLight.rgb
                                    else Colors.White
                                ),
                            text = "- $it"
                        )
                    }
                }
                itemsCount = 0
                subList = mutableListOf<String>()
            } else if (itemsCount < 4 && index == list.lastIndex) {
                Column(
//                    modifier = Modifier.height(300.px),
                    verticalArrangement = Arrangement.spacedBy(5.px)
                ) {
                    subList.forEach {
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Normal)
                                .fontSize(18.px)
                                .color(
                                    if (colorMode.isLight) Theme.PrimaryLight.rgb
                                    else Colors.White
                                ),
                            text = it
                        )
                    }
                }
                itemsCount = 0
            }

        }


    }
}


@Composable
fun randomTranslation(
    port1:(Int)->Unit,
    port2:(Int)->Unit,
    port3:(Int)->Unit,
    port4:(Int)->Unit,
    port5:(Int)->Unit,
    port6:(Int)->Unit
) {


    // Launch effect to update the translations
    LaunchedEffect(Unit) {
        while (true){
            delay(2000)
            //center alignment
            port1((-20..20).random())
            port2((-20..20).random())

            delay(1000)
            //start alignment
            port3((5..30).random())
            port4((5..30).random())

            delay(1000)
            //end alignment
            port5((-30..5).random())
            port6((-30..5).random())
        }
    }
}


@Composable
fun translatedIcons(translatePorts: TranslatePortsState,modifier: Modifier) {

    Box(
        modifier =modifier
    ){
        //center
        Box(modifier = Modifier.fillMaxSize()){

            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.Center)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 5100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port1).vw, ty = (translatePorts.port2).vh)
                ,
                src = Res.Image.android_ic
            )

            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.Center)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port2-(Random.nextInt(0,10))).vw, ty = (translatePorts.port1+(Random.nextInt(0,10))).vh)
                ,
                src = Res.Image.compos_ic
            )


            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.Center)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port2-(Random.nextInt(0,6))).vw, ty = (translatePorts.port1-(Random.nextInt(0,3))).vh)
                ,
                src = Res.Image.c_plus
            )


            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.Center)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port1-(Random.nextInt(0,11))).vw, ty = (translatePorts.port2+(Random.nextInt(0,4))).vh)
                ,
                src = Res.Image.figma
            )


            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.Center)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port2/(Random.nextInt(0,2))).vw, ty = (translatePorts.port1-(Random.nextInt(0,4))).vh)
                ,
                src = Res.Image.vs_code
            )
        }


        //top start
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.TopStart)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port3).vw, ty = (translatePorts.port4).vh)
                ,
                src = Res.Image.kotlin_ic
            )
            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.TopStart)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port4+(0..20).random()).vw, ty = (translatePorts.port3).vh)
                ,
                src = Res.Image.java_ic
            )



            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.TopStart)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port3+(0..8).random()).vw, ty = (translatePorts.port4+(0..34).random()).vh)
                ,
                src = Res.Image.intellij
            )


            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.TopStart)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port3).vw, ty = (translatePorts.port4+(0..40).random()).vh)
                ,
                src = Res.Image.mongo_compass
            )

        }



        //bottom end
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.BottomEnd)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port5).vw, ty = (translatePorts.port6).vh)
                ,
                src = Res.Image.Github
            )

            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.BottomEnd)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port6-(0..30).random()).vw, ty = (translatePorts.port5-(0..24).random()).vh)
                ,
                src = Res.Image.android_studio_ic
            )

            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.BottomEnd)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port5-(0..17).random()).vw, ty = (translatePorts.port6-(0..40).random()).vh)
                ,
                src = Res.Image.postmen
            )




            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.BottomEnd)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port6-(0..13).random()).vw, ty = (translatePorts.port5-(0..21).random()).vh)
                ,
                src = Res.Image.python
            )


            Image(
                modifier = Modifier
                    .size(64.px)
//                .zIndex(2)
                    .align(Alignment.BottomEnd)
                    .transition(
                        Transition.of(
                            property = TransitionProperty.All.toString(),
                            duration = 4100.ms
                        )
                    )
                    .translate(tx = (translatePorts.port5).vw, ty = (translatePorts.port5-(0..43).random()).vh)
                ,
                src = Res.Image.sql
            )

        }

    }
}


//@Composable
//fun randomTranslation(
//    onTranslateXList: (MutableList<Int>) -> Unit,
//    onTranslateYList: (MutableList<Int>) -> Unit,
//) {
//    val scope = rememberCoroutineScope()
//
//
//    val xList = mutableListOf(
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//    )
//    val yList = mutableListOf(
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//        0,
//    )
//    while (true) {
//        xList.forEachIndexed { index: Int, i: Int ->
//            scope.launch {
//                delay(5000)
//                val min = -45
//                val max = 45
//                xList[index] = ((min..max).random())
//            }
//        }
//
//        yList.forEachIndexed { index: Int, i: Int ->
//            scope.launch {
//                delay(5000)
//                val min = -45
//                val max = 45
//                yList[index] = ((min..max).random())
//            }
//        }
//        onTranslateXList(xList)
//        onTranslateYList(yList)
//
//    }
//}


//    onTranslateX: (Int) -> Unit,
//    onTranslateY: (Int) -> Unit,
//    var translateX by remember { mutableStateOf(0) }
//    var translateY by remember { mutableStateOf(0) }
//    var directionX by remember { mutableStateOf(1) } // 1 for right, -1 for left
//    var directionY by remember { mutableStateOf(1) } // 1 for down, -1 for up


//
////
////    scope.launch {
////        while (true) {
////            delay(5000)
////            val min = -45
////            val max = 45
////            translateX = ((min..max).random()) * directionX
////            translateY = ((min..max).random()) * directionY
////
////
////            if (translateX >= 46) {
////                directionX = -1
////            } else if (translateX <= 0) {
////                directionX = 1
////            }
////
////            if (translateY >= 46) {
////                directionY = -1
////            } else if (translateY <= 0) {
////                directionY = 1
////            }
////            onTranslateX(translateX)
////            onTranslateY(translateY)
////        }
////    }
//
//
//}
