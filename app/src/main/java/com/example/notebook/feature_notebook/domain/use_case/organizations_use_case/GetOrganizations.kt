package com.example.notebook.feature_notebook.domain.use_case.organizations_use_case

import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.entities.Organization
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import com.example.notebook.feature_notebook.domain.util.SearchType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetOrganizations(
    private val repository: NotebookRepository
) {
    operator fun invoke(
        searchQuery: String
    ): Flow<List<OrganizationInfo>> {
        return repository.getOrganizations(searchQuery).map { organizations ->
            organizations.sortedBy {
                it.organizationName
            }
        }
    }
}