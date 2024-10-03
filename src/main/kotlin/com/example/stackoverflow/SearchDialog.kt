package com.example.stackoverflow

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel

class SearchDialog(project: Project?) : DialogBuilder(project) {
    private val model = Model()

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

    private fun searchPanel(): DialogPanel {
        return panel {
            row {
                label(StackOverflowBundle.message("search.query.text.label"))
                textField().bindText(model::searchText)
                    .errorOnApply(StackOverflowBundle.message("search.message.query.required")) {
                        it.text == null || it.text.isBlank()
                    }
            }
        }
    }

    data class Model(var searchText: String = "")
}