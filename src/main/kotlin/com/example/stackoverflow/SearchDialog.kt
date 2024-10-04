package com.example.stackoverflow

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel

class SearchDialog(project: Project?, language: String) : DialogBuilder(project) {
    private val model = Model(searchLanguage = language)

    init {
        setTitle(StackOverflowBundle.message("search.dialog.title"))
        setCenterPanel(searchPanel())
        addOkAction()
        addCancelAction()
    }

    val searchText: String
        get() {
            return model.searchText
        }

    val searchLanguage: String
        get() {
            return model.searchLanguage
        }

    private fun searchPanel(): DialogPanel {
        return panel {
            row(StackOverflowBundle.message("search.query.text.label")) {
                textField().bindText(model::searchText)
                    .errorOnApply(StackOverflowBundle.message("search.message.query.required")) {
                        it.text == null || it.text.isBlank()
                    }
            }
            row(StackOverflowBundle.message("search.language.text.label")) {
                textField().bindText(model::searchLanguage)
            }
        }
    }

    data class Model(var searchText: String = "", var searchLanguage: String)
}