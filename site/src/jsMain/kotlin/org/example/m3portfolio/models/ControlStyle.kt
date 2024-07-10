package org.example.m3portfolio.models

sealed class ControlStyle(val style:String) {



    data class Bold(val selectedText: String?) : ControlStyle(
        style = "<strong>$selectedText</strong>"
    )

    data class Italic(val selectedText: String?) : ControlStyle(
        style = "<em>$selectedText</em>"
    )



    data class Link(
        val selectedText: String?,
        val href: String,
        val title: String
    ) : ControlStyle(
        style = "<a href=\"$href\" title=\"$title\">$selectedText</a>"
    )



    data class Title(val selectedText: String?) : ControlStyle(
        style = "<h1><strong>$selectedText</strong></h1>"
    )

    data class Subtitle(val selectedText: String?) : ControlStyle(
        style = "<h3>$selectedText</h3>"
    )

    data class Image(
        val selectedText: String?,
        val imageUrl: String,
        val alt: String
    ) : ControlStyle(
        style = "<img src=\"$imageUrl\" alt=\"$alt\" style=\"max-width: 100%\">$selectedText</img>"
    )

    data class Break(val selectedText: String?) : ControlStyle(
        style = "$selectedText<br>"
    )




}