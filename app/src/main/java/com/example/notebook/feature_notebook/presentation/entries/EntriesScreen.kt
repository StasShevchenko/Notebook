package com.example.notebook.feature_notebook.presentation.entries.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.feature_notebook.presentation.add_edit_entry.components.AddEditEntryScreen
import com.example.notebook.feature_notebook.presentation.destinations.AddEditEntryScreenDestination
import com.example.notebook.feature_notebook.presentation.entries.EntriesEvent
import com.example.notebook.feature_notebook.presentation.entries.EntriesViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Destination(start = true)
@Composable
fun EntriesScreen(
    navigator: DestinationsNavigator,
    viewModel: EntriesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.statusBarsPadding().navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddEditEntryScreenDestination())
                },
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add entry")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    searchQuery = state.searchType.searchQuery,
                    searchType = state.searchType,
                    orderType = state.entriesOrder,
                    expandedState = state.isSearchSectionVisible,
                    onSearchChanged = {
                        viewModel.onEvent(EntriesEvent.Search(it))
                    },
                    onOrderChanged = {
                        viewModel.onEvent(EntriesEvent.Order(it))
                    },
                    onExpandedStateChanged = {
                        viewModel.onEvent(EntriesEvent.ToggleSearchSection)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (viewModel.isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colors.primary
                    )
                }
            }
            if(viewModel.state.value.entries.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize().alpha(0.7f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text("Список пуст :(")
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 12.dp),
                contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp)

            ) {
                items(state.entries, key = {it.peopleId}) { entry ->

                    EntryItem(
                        navigator = navigator,
                        entry = entry,
                        onFavouriteChanged = {
                            viewModel.onEvent(EntriesEvent.Favourite(it))
                        },
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = spring()
                        ),
                        onDeleteEntry ={ entry ->
                            viewModel.onEvent(EntriesEvent.DeleteEntry(entry))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Запись удалена",
                                    actionLabel = "Отмена"
                                )
                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(EntriesEvent.RestoreEntry)
                                }
                            }
                        }
                    )


                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )
                }
            }
        }
    }

}