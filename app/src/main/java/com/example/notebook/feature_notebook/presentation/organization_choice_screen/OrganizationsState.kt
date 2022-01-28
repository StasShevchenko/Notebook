package com.example.notebook.feature_notebook.presentation.organization_choice_screen

import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.entities.OrganizationType

data class OrganizationsState(
    val organizations: List<OrganizationInfo> = emptyList(),
    val choiceOrganization: Int = -1,
    val organizationsType: List<OrganizationType> = emptyList(),
    val organizationName: String = "",
    val workersAmount: Int = 0,
    val currentOrganizationType: OrganizationType? = null,
    val searchQuery: String = ""
)
