package org.example.m3portfolio.util

import kotlinx.browser.document
import org.example.m3portfolio.Ids
import org.example.m3portfolio.models.ControlStyle
import org.example.m3portfolio.models.EditorControlIcons
import org.w3c.dom.HTMLTextAreaElement


// update it when adding new text areas for others like descriptions and experiences and other
fun getEditor(id:String): HTMLTextAreaElement = document.getElementById(id) as HTMLTextAreaElement
fun applyToPreview(preview_id:String, editor_id:String) {
    document.getElementById(preview_id)?.innerHTML = getEditor(editor_id).value
}
//only bio add adjust if other
fun getSelectedIntRange(editor_id: String): IntRange? {
    val editor = getEditor(editor_id)
    val start = editor.selectionStart
    val end = editor.selectionEnd
    return if (start != null && end != null) {
        IntRange(start, (end - 1))
    } else null
}


//only bio add adjust if other
fun getSelectedText(editor_id: String): String? {
    //updating from the first fun getSelectedText()
// after getting the selection rang out side from the fun getSelectedIntRang()
    val range = getSelectedIntRange(editor_id)
    return if (range != null) {
        getEditor(editor_id).value.substring(range)
    } else null
}


//only bio add adjust if other
fun applyStyle(controlStyle: ControlStyle,editor_id: String,preview_id: String) {
    val selectedText = getSelectedText(editor_id)
    val selectedIntRange = getSelectedIntRange(editor_id)
    if (selectedIntRange != null && selectedText != null) {
        getEditor(editor_id).value = getEditor(editor_id).value.replaceRange(
            range = selectedIntRange,
            replacement = controlStyle.style
        )
        document.getElementById(preview_id)?.innerHTML = getEditor(editor_id).value
    }
}


fun applyControlStyle(
    editorControl: EditorControlIcons,
    editor_id: String,
    preview_id: String,
    onLinkViewClicked: () -> Unit,
    onImageViewClicked: () -> Unit
) {
    when (editorControl) {
        EditorControlIcons.BoLd -> {
            applyStyle(
                ControlStyle.Bold(selectedText = getSelectedText(editor_id)),
                editor_id = editor_id,
                preview_id = preview_id
            )
        }

        EditorControlIcons.Italic -> {
            applyStyle(
                ControlStyle.Italic(selectedText = getSelectedText(editor_id)),
                editor_id = editor_id,
                preview_id = preview_id
            )
        }

        EditorControlIcons.Link -> {
            onLinkViewClicked()
        }

        EditorControlIcons.Title -> {
            applyStyle(
                ControlStyle.Title(selectedText = getSelectedText(editor_id)),
                editor_id = editor_id,
                preview_id = preview_id
            )
        }

        EditorControlIcons.Subtitle -> {
            applyStyle(
                ControlStyle.Subtitle(selectedText = getSelectedText(editor_id)),
                editor_id = editor_id,
                preview_id = preview_id
            )
        }

        EditorControlIcons.Image -> {
            onImageViewClicked()
        }
    }
}

