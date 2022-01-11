package com.example.notebook.feature_notebook.presentation.entries

import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.util.EntryOrder
import com.example.notebook.feature_notebook.domain.util.SearchType

sealed class EntriesEvent{
    data class Search(val searchType: SearchType): EntriesEvent()
    data class Order(val entryOrder: EntryOrder): EntriesEvent()
    data class DeleteEntry(val entry: PeopleInfo): EntriesEvent()
    data class Favourite(val entry: PeopleInfo): EntriesEvent()
    object RestoreEntry: EntriesEvent()
    object ToggleSearchSection: EntriesEvent()
}
