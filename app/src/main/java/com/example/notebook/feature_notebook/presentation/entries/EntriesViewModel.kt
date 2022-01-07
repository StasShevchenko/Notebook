package com.example.notebook.feature_notebook.presentation.entries

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.model.toPeople
import com.example.notebook.feature_notebook.domain.use_case.NotebookUseCases
import com.example.notebook.feature_notebook.domain.util.EntryOrder
import com.example.notebook.feature_notebook.domain.util.OrderType
import com.example.notebook.feature_notebook.domain.util.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntriesViewModel @Inject constructor(
    private val notebookUseCases: NotebookUseCases
) : ViewModel() {

    private val _state = mutableStateOf<EntriesState>(EntriesState())
    val state: State<EntriesState> = _state

    private var recentlyDeletedEntry: PeopleInfo? = null

    private var getEntriesJob: Job? = null


    init {
        getEntries(SearchType.NameSearch(""), EntryOrder.Alphabet(OrderType.Descending))
    }

    fun onEvent(event: EntriesEvent) {
        when (event) {
            is EntriesEvent.DeleteEntry -> {
                viewModelScope.launch {
                    notebookUseCases.deleteEntry(event.entry.peopleId)
                    recentlyDeletedEntry = event.entry
                }
            }
            is EntriesEvent.Order -> {
                if (state.value.entriesOrder::class == event.entryOrder::class &&
                    state.value.entriesOrder.orderType == event.entryOrder.orderType
                ) {
                    return
                }

            }
            EntriesEvent.RestoreEntry -> {
                viewModelScope.launch {
                    notebookUseCases.addEntry(recentlyDeletedEntry.toPeople() ?: return@launch)
                    recentlyDeletedEntry = null
                }
            }
            is EntriesEvent.Search -> {
                if (state.value.searchType::class == event.searchType::class &&
                    state.value.searchType.searchQuery == event.searchType.searchQuery) {
                  return
                }
            }
            is EntriesEvent.ToggleSearchSection -> {
                _state.value = state.value.copy(
                    isSearchSectionVisible = !state.value.isSearchSectionVisible
                )
            }
        }
    }

    private fun getEntries(searchType: SearchType, entriesOrder: EntryOrder) {
        getEntriesJob?.cancel()
        getEntriesJob = notebookUseCases.getEntries(searchType, entriesOrder)
             .onEach { entries ->
                 _state.value = state.value.copy(
                     entries = entries,
                     entriesOrder = entriesOrder,
                     searchType = searchType
                 )
             }
             .launchIn(viewModelScope)
    }
}