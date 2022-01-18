package com.example.notebook.feature_notebook.presentation.add_edit_entry

sealed class AddEditEntryEvent{
    data class EnteredName(val name: String): AddEditEntryEvent()
    data class EnteredSecondName(val secondName: String): AddEditEntryEvent()
    data class EnteredPatronymic(val patronymic: String): AddEditEntryEvent()
    data class EnteredDate(val dateOfBirth: String): AddEditEntryEvent()
    data class EnteredAddress(val address: String): AddEditEntryEvent()
    data class EnteredPhoneNumber(val phoneNumber: String): AddEditEntryEvent()

}
