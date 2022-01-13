package com.example.notebook.feature_notebook.presentation.add_edit_entry

sealed class AddEditEntryEvent{
    data class EnteredName(val name: String): AddEditEntryEvent()
    data class EnteredSecondName(val name: String): AddEditEntryEvent()
    data class EnteredPatronymic(val name: String): AddEditEntryEvent()

}
