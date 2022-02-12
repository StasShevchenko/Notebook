package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.notebook.feature_notebook.domain.model.entities.Post
import com.example.notebook.feature_notebook.domain.model.entities.Relations

@Composable
fun PostChoiceField(
    contentList: List<Post>,
    value: String,
    onValueChange: (String) -> Unit,
    onItemChosen: (Post) -> Unit,
    onItemDelete: (Post) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current


    Column() {
        OutlinedTextField(
            value = value,
            onValueChange = { postName ->
                onValueChange(postName)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .onFocusChanged { focusState ->
                   expanded = focusState.isFocused
                },
            label = { Text("Должность") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            singleLine = true
        )
        AnimatedVisibility(visible = expanded and contentList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .heightIn(0.dp, TextFieldDefaults.MinHeight * 3)

            ) {
                items(contentList) { post ->
                    Row(
                        modifier = Modifier.padding(8.dp).clickable {
                            onItemChosen(post)
                            focusManager.clearFocus()
                            expanded = false
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = post.postName,
                            modifier = Modifier.weight(0.9f).padding(start=4.dp)
                        )
                        IconButton(
                            modifier = Modifier.weight(0.1f),
                            onClick = {
                                onItemDelete(post)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
            }
        }
    }
}