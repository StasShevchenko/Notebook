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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showDatePicker(
    modifier: Modifier = Modifier,
    errorStatus: Boolean,
    errorMessage: String,
    onFocusChange: () -> Unit,
    dateOfBirth: String,
    onDateChange: (String) -> Unit,
    context: Context = LocalContext.current
) {
    var dismissCounter = 0
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val year: Int
    val month: Int
    val day: Int

    var initialDate = Date()
    try {
        initialDate.time = SimpleDateFormat("dd.MM.yyyy").parse(dateOfBirth).time
    } catch (e: ParseException) {
        initialDate = Date()
    }

    val calendar = Calendar.getInstance()
    calendar.time = initialDate
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)


    val minCalendar = Calendar.getInstance()
    minCalendar.set(1950, 0, 1)
    val maxCalendar = Calendar.getInstance()
    maxCalendar.set(2015, 11, 31)

    val datePickerDialog = DatePickerDialog(
        context,
        R.style.CalenderViewCustom,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateChange(
                LocalDate.of(year, month + 1, dayOfMonth).format(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
                )
            )
            dismissCounter++
           onFocusChange()

        }, year, month, day,
    )
    datePickerDialog.setOnDismissListener {
        if(dismissCounter == 0)
        focusManager.clearFocus()
    }

    datePickerDialog.datePicker.minDate = minCalendar.timeInMillis
    datePickerDialog.datePicker.maxDate = maxCalendar.timeInMillis
    Row(
        modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    datePickerDialog.show()
                } else {
                    focusRequester.freeFocus()
                }

            }
            .focusable()
    ) {
        ReadonlyTextField(
            value = dateOfBirth,
            onValueChange = {},
            errorStatus = errorStatus,
            errorMessage = errorMessage,
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