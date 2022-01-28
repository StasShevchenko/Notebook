package com.example.notebook.feature_notebook.domain.use_case.organizations_use_case

import com.example.notebook.feature_notebook.domain.model.entities.OrganizationType
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository

class GetOrganizationTypes(
    private val repository: NotebookRepository
) {
    suspend operator fun invoke(): List<OrganizationType>{
        return repository.getOrganizationTypes()
    }
}