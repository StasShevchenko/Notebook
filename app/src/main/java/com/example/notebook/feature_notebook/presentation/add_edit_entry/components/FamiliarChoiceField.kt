package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.notebook.feature_notebook.domain.model.entities.Relations
import kotlin.math.exp

@Composable
fun FamiliarChoiceField(
    contentList: List<Relations>,
    onItemChosen: (Relations) -> Unit,
    chosenItem: Relations
) {
    var expanded by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    var width by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded) Icons.Filled.ArrowDropUp
    else Icons.Filled.ArrowDropDown

    Column() {
        Box(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = chosenItem.familiarType,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { layoutCoordinates ->
                        width = layoutCoordinates.size.toSize()
                    }
                    .onFocusChanged {
                        if (it.isFocused) {
                            expanded = true
                        }
                    },
                label = { Text("Тип отношений") },
                trailingIcon = {
                    Icon(icon, "")
                },
                readOnly = true
            )
            DropdownMenu(
                expanded = expanded,
                modifier = Modifier
                    .width(with(LocalDensity.current) { width.width.toDp()})
                    .padding(8.dp),
                onDismissRequest = {
                    expanded = false
                    focusManager.clearFocus()
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    contentList.forEach {
                        DropdownMenuItem(
                            onClick = {
                                onItemChosen(it)
                                focusManager.clearFocus()
                                expanded = !expanded
                            }) {
                            Text(text = it.familiarType)
                        }
                    }
                }
            }
        }
    }
}

