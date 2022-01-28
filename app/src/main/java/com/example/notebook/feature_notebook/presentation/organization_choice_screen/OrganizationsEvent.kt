package com.example.notebook.feature_notebook.presentation.organization_choice_screen

import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.entities.Organization
import com.example.notebook.feature_notebook.domain.model.entities.OrganizationType

sealed class OrganizationsEvent{
    data class EnteredOrganizationName(val name: String): OrganizationsEvent()
    data class EnteredWorkersAmount(val workersAmount: Int): OrganizationsEvent()
    data class ChosenOrganizationType(val organizationType: OrganizationType): OrganizationsEvent()
    data class SearchOrganization(val searchQuery: String): OrganizationsEvent()
    data class ChosenOrganization(val organizationId: Int): OrganizationsEvent()
    data class DeleteOrganization(val organizationInfo: OrganizationInfo): OrganizationsEvent()
    object RestoreOrganization: OrganizationsEvent()
    object SaveOrganization: OrganizationsEvent()
}
