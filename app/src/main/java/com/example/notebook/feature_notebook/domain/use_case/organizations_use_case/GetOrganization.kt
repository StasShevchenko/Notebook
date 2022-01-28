package com.example.notebook.feature_notebook.domain.use_case.organizations_use_case

import com.example.notebook.feature_notebook.domain.repository.NotebookRepository

class GetOrganization(
    private val repository: NotebookRepository
) {
    suspend operator fun invoke(organizationId: Int) {
        repository.getOrganizationById(organizationId)
    }
}