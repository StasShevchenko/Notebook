package com.example.notebook.feature_notebook.presentation.organization_choice_screen.components

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.notebook.feature_notebook.presentation.organization_choice_screen.OrganizationChoiceScreenArguments
import com.example.notebook.feature_notebook.presentation.organization_choice_screen.OrganizationsEvent
import com.example.notebook.feature_notebook.presentation.organization_choice_screen.OrganizationsViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.google.gson.Gson
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Destination(navArgsDelegate = OrganizationChoiceScreenArguments::class)
@Composable
fun OrganizationsScreen(
    navigator: DestinationsNavigator,
    navController: NavController,
    viewModel: OrganizationsViewModel = hiltViewModel()
) {


    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(

                onClick = {
                    if (state.choiceOrganization != -1) {
                        val organizationInfo = state.organizations.find {
                            it.organizationId == state.choiceOrganization
                        }
                        val json = Uri.encode(Gson().toJson(organizationInfo))
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("organizationInfo", organizationInfo)
                        navController.popBackStack()
                    }
                },
                backgroundColor = if (state.choiceOrganization != -1) MaterialTheme.colors.secondary
                else MaterialTheme.colors.background,

                ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "")
            }
        },
        scaffoldState = scaffoldState
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Выбор организации",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize)
            )

            OrganizationSearchBar(
                searchQuery = state.searchQuery,
                onSearchChanged = { searchQuery ->
                    viewModel.onEvent(
                        OrganizationsEvent.SearchOrganization(searchQuery)
                    )
                })

            AddOrganization(
                organizationTypes = state.organizationsType,
                organizationName = state.organizationName,
                onOrganizationNameChanged = { organizationName ->
                    viewModel.onEvent(
                        OrganizationsEvent.EnteredOrganizationName(organizationName)
                    )
                },
                workersAmount = state.workersAmount,
                onWorkersAmountChanged = { workersAmount ->
                    viewModel.onEvent(OrganizationsEvent.EnteredWorkersAmount(workersAmount))
                },
                onOrganizationSave = {
                    viewModel.onEvent(OrganizationsEvent.SaveOrganization)
                },
                chosenOrganizationType = state.currentOrganizationType,
                onOrganizationTypeChoice = { chosenOrganizationType ->
                    viewModel.onEvent(
                        OrganizationsEvent.ChosenOrganizationType(chosenOrganizationType)
                    )
                },
                nameFieldErrorMessage = viewModel.nameFieldErrorMessage.value,
                nameFieldErrorStatus = viewModel.nameFieldErrorStatus.value,
                workersAmountFieldErrorMessage = viewModel.workersAmountFieldErrorMessage.value,
                workersAmountFieldErrorStatus = viewModel.workersAmountFieldErrorStatus.value
            )

            LazyColumn() {
                itemsIndexed(state.organizations, key = { index, item ->
                    item.organizationId
                }) { index, item ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewModel.onEvent(OrganizationsEvent.DeleteOrganization(item))
                                scope.launch{
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Организация удалена",
                                        actionLabel = "Отмена"
                                    )
                                    if(result == SnackbarResult.ActionPerformed){
                                        viewModel.onEvent(OrganizationsEvent.RestoreOrganization)
                                    }
                                }
                            }
                            true
                        }
                    )

                    val dismissDirection = setOf(DismissDirection.EndToStart)

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color = when (dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .background(color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp)
                                )
                            }
                        },
                        dismissContent = {
                            OrganizationItem(
                                isChosen = item.organizationId == state.choiceOrganization,
                                item = item,
                                onOrganizationChoice = { organizationId ->
                                    viewModel.onEvent(
                                        OrganizationsEvent.ChosenOrganization(
                                            organizationId
                                        )
                                    )
                                }
                            )
                        },
                        directions = dismissDirection
                    )


                }
            }
        }
    }
}