package com.example.notebook.feature_notebook.domain.use_case.entries_use_case

import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository

class DeleteEntry(
    private val repository: NotebookRepository
) {
    suspend operator fun invoke(entryId: Int){
        repository.deleteEntry(entryId)
    }
}