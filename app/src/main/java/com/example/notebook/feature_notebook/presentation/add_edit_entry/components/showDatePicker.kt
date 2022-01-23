package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.notebook.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showDatePicker(
    modifier: Modifier = Modifier,
    onFocusChange: () -> Unit,
    dateOfBirth: String,
    onDateChange: (String) -> Unit,
    context: Context = LocalContext.current
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val datePickerDialog = DatePickerDialog(
        context,
        R.style.CalenderViewCustom,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateChange(
                LocalDate.of(year, month + 1, dayOfMonth).format(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
                )
            )
            onFocusChange()

        }, year, month, day,

    )

    Row(
        modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    datePickerDialog.show()
                }
            }
            .focusable()
    ) {
        ReadonlyTextField(
            value = dateOfBirth,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = {
                focusRequester.requestFocus()
            },
            label = { Text("Дата рождения") }
        )
    }


}