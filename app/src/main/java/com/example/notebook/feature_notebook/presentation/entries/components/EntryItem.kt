package com.example.notebook.feature_notebook.presentation.entries.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.presentation.destinations.AddEditEntryScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun EntryItem(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    onFavouriteChanged: (PeopleInfo) -> Unit,
    onDeleteEntry: (PeopleInfo) -> Unit,
    entry: PeopleInfo
) {


    var expandedState by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(
                    300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column() {
            Surface(color = MaterialTheme.colors.primary) {
                Row(
                    modifier = Modifier.heightIn(min = 60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(6f)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "${entry.secondName} ${entry.name} ${entry.patronymic}",
                            style = TextStyle(
                                color = MaterialTheme.colors.onSurface,
                                fontSize = MaterialTheme.typography.body1.fontSize
                            )

                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row() {
                            if (entry.organizationName != null) {
                                Text(text = entry.organizationName)
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            if (entry.postName != null) {
                                Text(text = entry.postName)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(

                            modifier = Modifier
                                .size(22.dp),
                            onClick = {
                                onFavouriteChanged(entry.copy(favourite = !entry.favourite))
                            },

                            ) {
                            Icon(
                                imageVector = if (entry.favourite) Icons.Default.Star else Icons.Default.StarOutline,
                                contentDescription = ""
                            )
                        }
                    }
                }


            }
            if (expandedState) {
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Номер телефона: ${entry.phoneNumber}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Адрес: ${entry.address}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Дата рождения: ${entry.dateOfBirth}")
                    Spacer(modifier = Modifier.height(4.dp))
                    if (entry.organizationName != null) {
                        Divider()
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Количество сотрудников в организации: ${entry.workersAmount}")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Тип организации: ${entry.organizationType}")
                        Divider()
                    }
                    if (entry.relativeType != "") {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Родство: ${entry.relativeType}")

                    }
                    if (entry.familiarType != "") {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Отношения: ${entry.familiarType}",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    if (entry.familiarType != "" || entry.relativeType != "") {
                        Divider()
                    }
                    Row() {
                        IconButton(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(30.dp),
                            onClick = {
                                navigator.navigate(AddEditEntryScreenDestination(entry))
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(30.dp),
                            onClick = {
                                onDeleteEntry(entry)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }

                }
            }

        }
    }

}






