package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryEvent
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryScreenArguments
import com.example.notebook.feature_notebook.presentation.add_edit_entry.AddEditEntryViewModel
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Destination(navArgsDelegate = AddEditEntryScreenArguments::class)
@Composable
fun AddEditEntryScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditEntryViewModel = hiltViewModel(),
) {
    Surface(
        color = MaterialTheme.colors.surface
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
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = viewModel.name.value,
                onValueChange = { name ->
                         viewModel.onEvent(AddEditEntryEvent.EnteredName(name))
                },
                label = { Text("Имя") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = viewModel.secondName.value,
                onValueChange = {secondName ->
                    viewModel.onEvent(AddEditEntryEvent.EnteredSecondName(secondName))
                },
                label = { Text("Фамилия") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = viewModel.patronymic.value,
                onValueChange = {patronymic ->
                     viewModel.onEvent(AddEditEntryEvent.EnteredPatronymic(patronymic))
                },
                label = { Text("Отчество") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))



            showDatePicker(dateOfBirth = viewModel.dateOfBirth.value, onDateChange = { dateOfBirth ->
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
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = viewModel.phoneNumber.value,
                onValueChange = { phoneNumber ->
                       viewModel.onEvent(AddEditEntryEvent.EnteredPhoneNumber(phoneNumber))
                },
                label = { Text("Номер телефона") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { /*TODO*/ }
            ) {
                Text("Организация")
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


/*
@ExperimentalMaterialApi
@Preview
@Composable
fun AddEditPreview() {
    AddEditEntryScreen()
}
 */