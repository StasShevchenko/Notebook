package com.example.notebook.feature_notebook.domain.use_case.entries_use_case

import com.example.notebook.feature_notebook.domain.model.InvalidEntryException
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import kotlin.jvm.Throws

class AddEntry(
    private val repository: NotebookRepository
) {
    suspend operator fun invoke(entry: People){
        repository.insertEntry(entry)
    }
}