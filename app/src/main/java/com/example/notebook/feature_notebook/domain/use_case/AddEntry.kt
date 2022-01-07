package com.example.notebook.feature_notebook.domain.use_case

import com.example.notebook.feature_notebook.domain.model.InvalidEntryException
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import kotlin.jvm.Throws

class AddEntry(
    private val repository: NotebookRepository
) {

    @Throws(InvalidEntryException::class)
    suspend operator fun invoke(entry: People){
        if (entry.name.isBlank()) {
        throw InvalidEntryException("Имя не может быть пустым.")
        }
        if (entry.secondName.isBlank()) {
            throw InvalidEntryException("Фамилия не может быть пустой.")
        }
        if (entry.patronymic.isBlank()) {
            throw InvalidEntryException("Отчество не может быть пустым.")
        }
        repository.insertEntry(entry)
    }
}