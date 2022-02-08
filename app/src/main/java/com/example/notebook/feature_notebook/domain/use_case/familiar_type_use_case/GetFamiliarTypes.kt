package com.example.notebook.feature_notebook.domain.use_case.familiar_type_use_case

import com.example.notebook.feature_notebook.domain.model.entities.Relations
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository

class GetFamiliarTypes(
    private val repository: NotebookRepository
) {
     suspend operator fun invoke(): List<Relations>{
        return repository.getRelations()
    }
}