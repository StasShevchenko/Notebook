package com.example.notebook.feature_notebook.domain.use_case

import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import com.example.notebook.feature_notebook.domain.util.EntryOrder
import com.example.notebook.feature_notebook.domain.util.OrderType
import com.example.notebook.feature_notebook.domain.util.SearchType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetEntries(
    private val repository: NotebookRepository
) {
    operator fun invoke(
        searchType: SearchType = SearchType.NameSearch(""),
        entryOrder: EntryOrder = EntryOrder.Alphabet(OrderType.Descending)
    ): Flow<List<PeopleInfo>>{
    return repository.getEntries(searchType).map { entries ->
        when(entryOrder.orderType){
            is OrderType.Ascending -> {
                when(entryOrder){
                    is EntryOrder.Alphabet -> entries.sortedBy { it.name.lowercase() }
                    is EntryOrder.Date -> entries.sortedBy { it.timestamp }
                }
            }
            is OrderType.Descending -> {
                when(entryOrder){
                    is EntryOrder.Alphabet -> entries.sortedByDescending { it.name.lowercase() }
                    is EntryOrder.Date -> entries.sortedByDescending { it.timestamp }
                }
            }
        }.sortedByDescending { it.favourite }
    }
    }
}