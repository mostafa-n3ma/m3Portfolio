package org.example.m3portfolio.util

import kotlinx.browser.document
import org.example.m3portfolio.Ids
import org.example.m3portfolio.models.ControlStyle
import org.example.m3portfolio.models.EditorControlIcons
import org.w3c.dom.HTMLTextAreaElement


// update it when adding new text areas for others like descriptions and experiences and other
fun getEditor(): HTMLTextAreaElement = document.getElementById(Ids.info_BiotextArea) as HTMLTextAreaElement
fun applyToBioDiv() {
    document.getElementById(Ids.info_BiotextAreaDiv)?.innerHTML = getEditor().value
}
//only bio add adjust if other
fun getSelectedIntRange(): IntRange? {
    val editor = getEditor()
    val start = editor.selectionStart
    val end = editor.selectionEnd
    return if (start != null && end != null) {
        IntRange(start, (end - 1))
    } else null
}


//only bio add adjust if other
fun getSelectedText(): String? {
    //updating from the first fun getSelectedText()
// after getting the selection rang out side from the fun getSelectedIntRang()
    val range = getSelectedIntRange()
    return if (range != null) {
        getEditor().value.substring(range)
    } else null
}


//only bio add adjust if other
fun applyStyle(controlStyle: ControlStyle) {
    val selectedText = getSelectedText()
    val selectedIntRange = getSelectedIntRange()
    if (selectedIntRange != null && selectedText != null) {
        getEditor().value = getEditor().value.replaceRange(
            range = selectedIntRange,
            replacement = controlStyle.style
        )
        document.getElementById(Ids.info_BiotextAreaDiv)?.innerHTML = getEditor().value
    }
}


fun applyControlStyle(
    editorControl: EditorControlIcons,
    onLinkViewClicked: () -> Unit,
    onImageViewClicked: () -> Unit
) {
    when (editorControl) {
        EditorControlIcons.BoLd -> {
            applyStyle(
                ControlStyle.Bold(selectedText = getSelectedText())
            )
        }

        EditorControlIcons.Italic -> {
            applyStyle(
                ControlStyle.Italic(selectedText = getSelectedText())
            )
        }

        EditorControlIcons.Link -> {
            onLinkViewClicked()
        }

        EditorControlIcons.Title -> {
            applyStyle(
                ControlStyle.Title(selectedText = getSelectedText())
            )
        }

        EditorControlIcons.Subtitle -> {
            applyStyle(
                ControlStyle.Subtitle(selectedText = getSelectedText())
            )
        }

        EditorControlIcons.Image -> {
            onImageViewClicked()
        }
    }
}

