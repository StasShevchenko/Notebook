package com.example.notebook.feature_notebook.presentation.add_edit_entry

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.navArgument
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.use_case.NotebookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditEntryViewModel @Inject constructor(
    private val notebookUseCases: NotebookUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {




    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _secondName = mutableStateOf("")
    val secondName: State<String> = _secondName
    
    private val _patronymic = mutableStateOf<String>("")
    val patronymic: State<String> = _patronymic
    
    private val _dataOfBirth = mutableStateOf<String>("")
    val dataOfBirth: State<String> = _dataOfBirth
    
    private val _address = mutableStateOf<String>("")
    val address: State<String> = _address

    init{
        savedStateHandle.get<PeopleInfo>("entry")?.let { entry ->
            _name.value = entry.name
        }
    }



}