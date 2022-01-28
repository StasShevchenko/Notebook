package com.example.notebook.feature_notebook.presentation.entries

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.toPeople
import com.example.notebook.feature_notebook.domain.use_case.entries_use_case.EntryUseCases
import com.example.notebook.feature_notebook.domain.util.EntryOrder
import com.example.notebook.feature_notebook.domain.util.OrderType
import com.example.notebook.feature_notebook.domain.util.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntriesViewModel @Inject constructor(
    private val entryUseCases: EntryUseCases
) : ViewModel() {

    private var _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading


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
                    entryUseCases.deleteEntry(event.entry.peopleId)
                    recentlyDeletedEntry = event.entry
                }
            }
            is EntriesEvent.Order -> {
                if (state.value.entriesOrder::class == event.entryOrder::class &&
                    state.value.entriesOrder.orderType == event.entryOrder.orderType
                ) {
                    return
                }
                else getEntries(state.value.searchType, event.entryOrder)

            }
            EntriesEvent.RestoreEntry -> {
                viewModelScope.launch {
                    entryUseCases.addEntry(recentlyDeletedEntry.toPeople() ?: return@launch)
                    recentlyDeletedEntry = null
                }
            }
            is EntriesEvent.Search -> {
                if (state.value.searchType::class == event.searchType::class &&
                    state.value.searchType.searchQuery == event.searchType.searchQuery) {
                  return
                }
                else
                    _state.value = state.value.copy(searchType = event.searchType)
                    getEntries(event.searchType, state.value.entriesOrder)

            }
            is EntriesEvent.ToggleSearchSection -> {
                _state.value = state.value.copy(
                    isSearchSectionVisible = !state.value.isSearchSectionVisible
                )
            }
            is EntriesEvent.Favourite -> {
                viewModelScope.launch {
                    entryUseCases.addEntry(event.entry.toPeople() ?: return@launch)
                }

            }
        }
    }

    private fun getEntries(searchType: SearchType, entriesOrder: EntryOrder) {
        getEntriesJob?.cancel()
        getEntriesJob = entryUseCases.getEntries(searchType, entriesOrder)
             .onEach { entries ->
                 _state.value = state.value.copy(
                     entries = entries,
                     entriesOrder = entriesOrder,
                     searchType = searchType,
                 )
                 _isLoading.value = false
             }
             .launchIn(viewModelScope)
    }
}