package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.domain.model.entities.Relations

@Composable
fun FamiliarChoiceField(
    contentList: List<Relations>,
    onItemChosen: (Relations) -> Unit,
    chosenItem: Relations
) {

    var expanded by remember { mutableStateOf(false)}

    val icon = if(expanded) Icons.Filled.ArrowDropDown
    else Icons.Filled.ArrowDropDown

    Column(
        modifier = Modifier.
                clickable {
                    expanded = !expanded
                }
    ){
        OutlinedTextField(
            value = chosenItem.familiarType,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {Text("Тип отношений")},

            trailingIcon = {
                Icon(icon, "")
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            contentList.forEach{
                DropdownMenuItem(onClick = {
                    onItemChosen(it)
                }) {
                    Text(text = it.familiarType)
                }
            }

        }
    }
}