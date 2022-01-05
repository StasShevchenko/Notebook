package com.example.notebook.feature_notebook.domain.use_case

import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import com.example.notebook.feature_notebook.domain.util.EntryOrder
import com.example.notebook.feature_notebook.domain.util.OrderType
import com.example.notebook.feature_notebook.domain.util.SearchType
import kotlinx.coroutines.flow.Flow

/*
class GetEntries(
    private val repository: NotebookRepository
) {
    operator fun invoke(
        searchType: SearchType = SearchType.NameSearch(""),
        entryOrder: EntryOrder = EntryOrder.Alphabet(OrderType.Descending)
    ): Flow<List<PeopleInfo>>{

    }
} */