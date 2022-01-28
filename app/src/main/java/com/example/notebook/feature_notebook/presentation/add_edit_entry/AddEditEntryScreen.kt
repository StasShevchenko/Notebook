package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryEvent
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryScreenArguments
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryViewModel
import com.example.notebook.feature_notebook.presentation.destinations.OrganizationsScreenDestination
import com.google.accompanist.insets.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Destination(navArgsDelegate = AddEditEntryScreenArguments::class)
@Composable
fun AddEditEntryScreen(
    navigator: DestinationsNavigator,
    navController: NavController,
    viewModel: AddEditEntryViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    val secondResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<OrganizationInfo>("organizationInfo")

    secondResult?.value?.let{ organizationInfo ->
        viewModel.onEvent(AddEditEntryEvent.GotBackResult(organizationInfo))
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditEntryViewModel.UiEvent.SaveNote -> {
                    navigator.navigateUp()
                }
            }
        }
    }

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsWithImePadding(),
                onClick = {
                    viewModel.onEvent((AddEditEntryEvent.SaveEntry))
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "")
            }
        },
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxSize()
        ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .verticalScroll(scrollState, true),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Информация о записи:",
                            style = TextStyle(fontSize = MaterialTheme.typography.h5.fontSize),
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            value = viewModel.name.value,
                            onValueChange = { name ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredName(name))
                            },
                            label = { Text("Имя") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)

                            })
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            value = viewModel.secondName.value,
                            onValueChange = { secondName ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredSecondName(secondName))
                            },
                            label = { Text("Фамилия") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            })
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            value = viewModel.patronymic.value,
                            onValueChange = { patronymic ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredPatronymic(patronymic))
                            },
                            label = { Text("Отчество") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            })
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        showDatePicker(
                            modifier = Modifier,
                            onFocusChange = {
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            dateOfBirth = viewModel.dateOfBirth.value,
                            onDateChange = { dateOfBirth ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredDate(dateOfBirth))
                            })

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            value = viewModel.address.value,
                            onValueChange = { address ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredAddress(address))
                            },
                            label = { Text("Адрес") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            })
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            value = viewModel.phoneNumber.value,
                            onValueChange = { phoneNumber ->
                                viewModel.onEvent(
                                    AddEditEntryEvent.EnteredPhoneNumber(
                                        phoneNumber
                                    )
                                )
                            },
                            label = { Text("Номер телефона") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            })
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = { navigator.navigate(OrganizationsScreenDestination(viewModel.organizationId)) }
                        ) {
                            if(viewModel.organizationId != -1){
                                Column(modifier = Modifier.fillMaxWidth()){
                                    Text(viewModel.organizationName)
                                    Text("Тип организации: ${viewModel.organizationType}")
                                    Text("Количество сотрудников: ${viewModel.workersAmount}")
                                }
                            }
                            else {
                                Text("Организация")
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = { /*TODO*/ }
                        ) {
                            Text("Должность")
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {

                        }
                        ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {

                        }
                    }
                }
            }
        }
    }
}