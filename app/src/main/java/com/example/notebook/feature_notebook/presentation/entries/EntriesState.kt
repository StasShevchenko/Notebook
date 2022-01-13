package com.example.notebook.feature_notebook.presentation.entries

import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.util.EntryOrder
import com.example.notebook.feature_notebook.domain.util.OrderType
import com.example.notebook.feature_notebook.domain.util.SearchType

data class EntriesState(
    val entries: List<PeopleInfo> = emptyList(),
    val searchType: SearchType = SearchType.NameSearch(""),
    val entriesOrder: EntryOrder = EntryOrder.Alphabet(OrderType.Descending),
    val isSearchSectionVisible: Boolean = false
)
