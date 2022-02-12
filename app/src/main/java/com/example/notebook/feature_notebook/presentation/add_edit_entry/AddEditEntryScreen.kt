package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryEvent
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryScreenArguments
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryViewModel
import com.example.notebook.feature_notebook.presentation.destinations.OrganizationsScreenDestination
import com.example.notebook.feature_notebook.presentation.util.OutlinedErrorTextField
import com.example.notebook.feature_notebook.presentation.util.PhoneNumberVisualTransformation
import com.google.accompanist.insets.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

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

    secondResult?.value?.let { organizationInfo ->
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
            modifier = Modifier
                .statusBarsPadding(),
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
                        .navigationBarsWithImePadding()
                        .verticalScroll(scrollState, true),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Информация о записи:",
                            style = TextStyle(fontSize = MaterialTheme.typography.h5.fontSize),
                            modifier = Modifier.padding(top = 8.dp)
                        )


                        OutlinedErrorTextField(
                            value = viewModel.name.value,
                            onValueChange = { name ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredName(name))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("Имя") },
                            isError = viewModel.nameErrorStatus.value,
                            errorMessage = viewModel.nameErrorMessage.value,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)

                            }),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedErrorTextField(
                            value = viewModel.secondName.value,
                            onValueChange = { secondName ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredSecondName(secondName))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("Фамилия") },
                            isError = viewModel.secondNameErrorStatus.value,
                            errorMessage = viewModel.secondNameErrorMessage.value,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedErrorTextField(
                            value = viewModel.patronymic.value,
                            onValueChange = { patronymic ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredPatronymic(patronymic))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("Отчество") },
                            isError = viewModel.patronymicErrorStatus.value,
                            errorMessage = viewModel.patronymicErrorMessage.value,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            singleLine = true
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
                            },
                            errorStatus = viewModel.dateOfBirthErrorStatus.value,
                            errorMessage = viewModel.dateOfBirthErrorMessage.value
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedErrorTextField(
                            value = viewModel.address.value,
                            onValueChange = { address ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredAddress(address))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("Адрес") },
                            isError = viewModel.addressErrorStatus.value,
                            errorMessage = viewModel.addressErrorMessage.value,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedErrorTextField(
                            value = viewModel.phoneNumber.value,
                            onValueChange = { phoneNumber ->
                                viewModel.onEvent(
                                    AddEditEntryEvent.EnteredPhoneNumber(
                                        phoneNumber
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("Номер телефона") },
                            isError = viewModel.phoneNumberErrorStatus.value,
                            errorMessage = viewModel.phoneNumberErrorMessage.value,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            singleLine = true,
                            visualTransformation = PhoneNumberVisualTransformation()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = { navigator.navigate(OrganizationsScreenDestination(viewModel.organizationId)) }
                        ) {
                            if (viewModel.organizationName != "") {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(viewModel.organizationName)
                                    Text("Тип организации: ${viewModel.organizationType}")
                                    Text("Количество сотрудников: ${viewModel.workersAmount}")
                                }
                            } else {
                                Text("Организация")
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        PostChoiceField(
                            contentList = viewModel.posts.value,
                            value = viewModel.postName.value,
                            onValueChange = { postName ->
                                viewModel.onEvent(AddEditEntryEvent.EnteredPostName(postName))
                            },
                            onItemChosen = { post ->
                                viewModel.onEvent(AddEditEntryEvent.ChosenPost(post))
                            },
                            onItemDelete = { post ->
                                viewModel.onEvent(AddEditEntryEvent.DeletedPost(post))
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        FamiliarChoiceField(
                            contentList = viewModel.relationsList,
                            onItemChosen = { familiarType ->
                                viewModel.onEvent(AddEditEntryEvent.ChosenFamiliarType(familiarType))
                            },
                            chosenItem = viewModel.familiarType.value
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        RelativeChoiceField(
                            contentList = viewModel.relativesList,
                            onItemChosen = { relativeType ->
                                viewModel.onEvent(AddEditEntryEvent.ChosenRelativeType(relativeType))
                            },
                            chosenItem = viewModel.relativeType.value
                        )
                    }
                }
            }
        }
    }
}

