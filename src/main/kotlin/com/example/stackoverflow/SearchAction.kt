package com.example.stackoverflow

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class SearchAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val language = event.getData(CommonDataKeys.PSI_FILE)?.language?.displayName?.lowercase()

        val editor = event.getRequiredData(CommonDataKeys.EDITOR)
        if (editor.caretModel.currentCaret.hasSelection()) {
            val selectedText = editor.caretModel.currentCaret.selectedText
            if (!selectedText.isNullOrBlank()) {
                browse(language, selectedText)
            }
        } else {
            val dialog = SearchDialog(event.project, language.toString())
            if (dialog.showAndGet()) {
                browse(dialog.searchLanguage, dialog.searchText)
            }
        }
    }

    private fun browse(language: String?, selectedText: String) {
        val languageQueryTag = if (language.isNullOrBlank()) "" else "+[${language}]"
        BrowserUtil.browse("https://stackoverflow.com/search?q=${languageQueryTag}${selectedText}")
    }

}