package com.example.notebook.feature_notebook.domain.use_case.entries_use_case

import com.example.notebook.feature_notebook.domain.model.InvalidEntryException
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import kotlin.jvm.Throws

class AddEntry(
    private val repository: NotebookRepository
) {

    @Throws(InvalidEntryException::class)
    suspend operator fun invoke(entry: People){
        if (entry.name.isBlank()) {
        throw InvalidEntryException("Необходимо заполнить имя!")
        }
        if (entry.secondName.isBlank()) {
            throw InvalidEntryException("Необходимо заполнить фамилию!")
        }
        if (entry.patronymic.isBlank()) {
            throw InvalidEntryException("Необходимо заполнить отчество!")
        }
        if(entry.dateOfBirth.isBlank()){
            throw InvalidEntryException("Выберите дату рождения!")
        }
        repository.insertEntry(entry)
    }
}