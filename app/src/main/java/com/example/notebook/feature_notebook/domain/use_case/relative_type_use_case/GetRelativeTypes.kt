package com.example.notebook.feature_notebook.domain.use_case.relative_type_use_case

import com.example.notebook.feature_notebook.domain.model.entities.Relatives
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository

class GetRelativeTypes(
    private val repository: NotebookRepository
) {
    suspend operator fun invoke(): List<Relatives>{
        return repository.getRelatives()
    }
}