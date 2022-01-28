package com.example.notebook.feature_notebook.domain.use_case.organizations_use_case

data class OrganizationUseCases(
    val addOrganization: AddOrganization,
    val deleteOrganization: DeleteOrganization,
    val getOrganizations: GetOrganizations,
    val getOrganizationTypes: GetOrganizationTypes,
    val getOrganization: GetOrganization
)
