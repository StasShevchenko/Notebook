package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.*
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyDateField(
    dateOfBirth: String,
    onDateChange: (String) -> Unit,
) {
    val dialogState = rememberMaterialDialogState()


    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ок")
            negativeButton("Отмена")
        }
    ) {

        datepicker(
            title = "Выберите дату"
        ) { date ->
            val formattedDate = date.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )
            onDateChange(TextFieldValue(formattedDate).text)
        }

    }



    Column() {
        ReadonlyTextField(
            value = dateOfBirth,
            onValueChange = { },
            onClick = {
                dialogState.show()
            },
            label = {
                Text(text = "Дата рождения")
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
    }

}