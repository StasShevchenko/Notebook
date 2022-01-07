package com.example.notebook.feature_notebook.domain.use_case

data class NotebookUseCases(
    val getEntries: GetEntries,
    val deleteEntry: DeleteEntry,
    val addEntry: AddEntry
)
