package com.example.notebook.feature_notebook.domain.use_case.organizations_use_case

import com.example.notebook.feature_notebook.domain.model.entities.Organization
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository

class AddOrganization(
    private val repository: NotebookRepository
) {
    suspend operator fun invoke(organization: Organization){
        repository.insertOrganization(organization)
    }
}