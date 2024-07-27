package org.example.m3portfolio.models

import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba

enum class Theme(
    val hex: String,
    val rgb: CSSColorValue
) {

    PrimaryLight(hex = "#3B4E9E", rgb = rgb(r = 59, g = 78, b = 158)),
    Them_bk_light(hex = "#F7F5F5", rgb = rgb(r = 247, g = 245, b = 245)),
    Item_bk_light(hex = "#FFFFFF", rgb = rgb(r = 255, g = 255, b = 255)),
    gradient_light_from(hex ="00D4FF" ,rgb = rgb(r = 0, g = 212, b = 255)),
    gradient_light_to(hex ="090979" ,rgb = rgb(r = 9, g = 9, b = 121)),


    PrimaryDark(hex = "#101B3C", rgb = rgb(r = 16, g = 27, b = 60)),
    Them_bk_dark(hex = "#44436A", rgb = rgb(r = 68, g = 67, b = 106)),
    Item_bk_dark(hex = "#F7F5F5", rgb = rgb(r = 247, g = 245, b = 245)),


    HalfBlack(hex = "#000000", rgba(r = 0, g = 0, b = 0, a = 0.5)),


}