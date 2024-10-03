package com.example.stackoverflow

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class SearchAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val language = event.getData(CommonDataKeys.PSI_FILE)?.language
        val languageQueryTag = "+[${language?.displayName?.lowercase()}]"

        val editor = event.getRequiredData(CommonDataKeys.EDITOR)
        if (editor.caretModel.currentCaret.hasSelection()) {
            val selectedText = editor.caretModel.currentCaret.selectedText
            if (!selectedText.isNullOrBlank()) {
                browse(languageQueryTag, selectedText)
                return
            }
        }

        val dialog = SearchDialog(event.project)
        if (dialog.showAndGet()) {
            browse(languageQueryTag, dialog.searchText)
        }
    }

    private fun browse(languageQueryTag: String, selectedText: String) {
        BrowserUtil.browse("https://stackoverflow.com/search?q=${languageQueryTag}${selectedText}")
    }

}