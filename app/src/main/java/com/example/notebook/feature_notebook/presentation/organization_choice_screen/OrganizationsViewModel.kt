package com.example.notebook.feature_notebook.presentation.organization_choice_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.Organization
import com.example.notebook.feature_notebook.domain.model.toOrganization
import com.example.notebook.feature_notebook.domain.use_case.organizations_use_case.OrganizationUseCases
import com.example.notebook.feature_notebook.domain.util.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationsViewModel @Inject constructor(
    private val organizationsUseCases: OrganizationUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = mutableStateOf<OrganizationsState>(OrganizationsState())
    val state: State<OrganizationsState> = _state

    private var getOrganizationsJob: Job? = null


    private val _nameFieldErrorStatus = mutableStateOf<Boolean>(false)
    val nameFieldErrorStatus: State<Boolean> = _nameFieldErrorStatus

    private val _nameFieldErrorMessage = mutableStateOf<String>("")
    val nameFieldErrorMessage: State<String> = _nameFieldErrorMessage


    private val _workersAmountFieldErrorStatus = mutableStateOf<Boolean>(false)
    val workersAmountFieldErrorStatus: State<Boolean> = _workersAmountFieldErrorStatus

    private val _workersAmountFieldErrorMessage = mutableStateOf<String>("")
    val workersAmountFieldErrorMessage: State<String> = _workersAmountFieldErrorMessage

    private var recentlyDeletedOrganizationInfo: OrganizationInfo? = null

    init {
        savedStateHandle.get<Int>("organizationId")?.let { organizationId ->
            _state.value = _state.value.copy(choiceOrganization = organizationId)
        }

        viewModelScope.launch {
            getOrganizations("")
            _state.value =
                state.value.copy(organizationsType = organizationsUseCases.getOrganizationTypes())
            _state.value =
                state.value.copy(currentOrganizationType = state.value.organizationsType[0])
        }

    }

    private fun getOrganizations(searchQuery: String) {
        getOrganizationsJob?.cancel()
        getOrganizationsJob = organizationsUseCases.getOrganizations(searchQuery)
            .onEach { organizations ->
                _state.value = state.value.copy(
                    organizations = organizations
                )

            }.launchIn(viewModelScope)
    }

    fun onEvent(event: OrganizationsEvent) {
        when (event) {
            is OrganizationsEvent.ChosenOrganizationType -> {
                _state.value = state.value.copy(currentOrganizationType = event.organizationType)
            }
            is OrganizationsEvent.EnteredOrganizationName -> {
                _nameFieldErrorStatus.value = false
                if (event.name.length > 25) {
                    _nameFieldErrorStatus.value = true
                    _nameFieldErrorMessage.value = "слишком длинное имя!"
                }
                else {
                    _state.value = state.value.copy(organizationName = event.name)
                }
            }
            is OrganizationsEvent.EnteredWorkersAmount -> {
                _workersAmountFieldErrorStatus.value = false
                if(event.workersAmount.length > 5){
                    _workersAmountFieldErrorStatus.value = true
                    _workersAmountFieldErrorMessage.value = "слишком большое число сотрудников!"
                }
                else{
                _state.value = state.value.copy(workersAmount = if(event.workersAmount.isBlank()) 0 else event.workersAmount.toInt() )
                }
            }
            OrganizationsEvent.SaveOrganization -> {
                viewModelScope.launch {

                    if (_state.value.workersAmount == 0) {
                        _workersAmountFieldErrorMessage.value = "данное поле не может быть пустым!"
                        _workersAmountFieldErrorStatus.value = true
                    }

                    if (_state.value.organizationName == "") {
                        _nameFieldErrorMessage.value = "данное поле не может быть пустым!"
                        _nameFieldErrorStatus.value = true
                    }

                    if (_state.value.organizations.find {
                            (it.organizationName == _state.value.organizationName)
                        } != null) {
                        _nameFieldErrorMessage.value = "данная организация уже существует!"
                        _nameFieldErrorStatus.value = true
                    }


                    if (!workersAmountFieldErrorStatus.value and !nameFieldErrorStatus.value) {
                        organizationsUseCases.addOrganization(
                            Organization(
                                organizationId = 0,
                                organizationName = _state.value.organizationName,
                                workersAmount = _state.value.workersAmount,
                                typeId = _state.value.currentOrganizationType!!.typeId
                            )
                        )
                        _state.value = state.value.copy(
                            organizationName = "",
                            workersAmount = 0,
                            currentOrganizationType = state.value.organizationsType[0]
                        )
                    }
                }
            }
            is OrganizationsEvent.SearchOrganization -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
                getOrganizations(state.value.searchQuery)
            }
            is OrganizationsEvent.ChosenOrganization -> {
                if (event.organizationId == state.value.choiceOrganization) _state.value =
                    state.value.copy(choiceOrganization = -1)
                else {
                    _state.value = state.value.copy(choiceOrganization = event.organizationId)
                }
            }
            is OrganizationsEvent.DeleteOrganization -> {
                recentlyDeletedOrganizationInfo = event.organizationInfo
                viewModelScope.launch {
                    organizationsUseCases.deleteOrganization(
                        recentlyDeletedOrganizationInfo.toOrganization()!!
                    )
                }
            }
            is OrganizationsEvent.RestoreOrganization -> {

                    viewModelScope.launch {
                        organizationsUseCases.addOrganization(
                            recentlyDeletedOrganizationInfo.toOrganization() ?: return@launch
                        )
                        recentlyDeletedOrganizationInfo = null
                    }
                }
            }
        }
    }


