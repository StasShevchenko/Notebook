package com.example.notebook.feature_notebook.presentation.add_edit_entry

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navArgument
import com.example.notebook.feature_notebook.domain.model.InvalidEntryException
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.use_case.NotebookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditEntryViewModel @Inject constructor(
    private val notebookUseCases: NotebookUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private var currentPeopleId: Int? = null

    private val _secondName = mutableStateOf("")
    val secondName: State<String> = _secondName

    private val _patronymic = mutableStateOf<String>("")
    val patronymic: State<String> = _patronymic

    private val _dateOfBirth = mutableStateOf<String>("")
    val dateOfBirth: State<String> = _dateOfBirth

    private val _phoneNumber = mutableStateOf<String>("")
    val phoneNumber: State<String> = _phoneNumber

    private val _address = mutableStateOf<String>("")
    val address: State<String> = _address


    fun onEvent(event: AddEditEntryEvent) {
        when (event) {
            is AddEditEntryEvent.EnteredAddress -> {
                _address.value = event.address
            }
            is AddEditEntryEvent.EnteredDate -> {
                _dateOfBirth.value = event.dateOfBirth
            }
            is AddEditEntryEvent.EnteredName -> {
                _name.value = event.name
            }
            is AddEditEntryEvent.EnteredPatronymic -> {
                _patronymic.value = event.patronymic
            }
            is AddEditEntryEvent.EnteredPhoneNumber -> {
                _phoneNumber.value = event.phoneNumber
            }
            is AddEditEntryEvent.EnteredSecondName -> {
                _secondName.value = event.secondName
            }
            AddEditEntryEvent.SaveEntry -> {
               viewModelScope.launch {
                   try{
                       notebookUseCases.addEntry(
                           People(
                              name = name.value,
                              secondName = secondName.value,
                              patronymic = secondName.value,
                               dateOfBirth = dateOfBirth.value,
                               address = address.value,
                               phoneNumber = phoneNumber.value,
                               timestamp = System.currentTimeMillis(),
                               organizationId = 1,
                               postId = 1,
                               relativeId = 1,
                               familiarId = 1
                           )
                       )
                   } catch (e: InvalidEntryException){

                   }
               }
            }
        }
    }



    init{
        savedStateHandle.get<PeopleInfo>("entry")?.let { entry ->
            currentPeopleId = entry.peopleId
            _name.value = entry.name
            _secondName.value = entry.secondName
            _patronymic.value = entry.patronymic
            _dateOfBirth.value = entry.dateOfBirth
            _address.value = entry.address
            _phoneNumber.value = entry.phoneNumber
        }
    }




}