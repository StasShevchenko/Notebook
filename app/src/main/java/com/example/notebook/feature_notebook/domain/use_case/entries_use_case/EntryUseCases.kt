package com.example.notebook.feature_notebook.domain.use_case.entries_use_case

data class EntryUseCases(
    val getEntries: GetEntries,
    val deleteEntry: DeleteEntry,
    val addEntry: AddEntry
)
