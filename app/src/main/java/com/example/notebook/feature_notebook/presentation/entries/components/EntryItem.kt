package com.example.notebook.feature_notebook.presentation.entries.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.People

@ExperimentalMaterialApi
@Composable
fun EntryItem(
    modifier: Modifier = Modifier,
    onFavouriteChanged: (PeopleInfo) -> Unit,
    entry: PeopleInfo
) {
    var expandedState by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .animateContentSize (
                animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
                )
            )
            .padding(12.dp),
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
                    modifier = Modifier.padding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(6f)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "${entry.secondName} ${entry.name} ${entry.patronymic}",
                            style = TextStyle(
                                color = MaterialTheme.colors.onSurface,
                                fontSize = MaterialTheme.typography.body1.fontSize
                            )

                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row() {
                            Text(text = entry.organizationName)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = entry.postName)
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
                    Divider()
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Количество сотрудников в организации: ${entry.workersAmount}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Тип организации: ${entry.organizationType}")
                    Divider()
                    if (entry.relativeType != "") {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Родство: ${entry.relativeType}")

                    }
                    if (entry.familiarType != "") {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Отношения: ${entry.familiarType}")
                    }
                }
            }

        }
    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun EntryItemPreview() {
    EntryItem(
        onFavouriteChanged = {},
        entry = PeopleInfo(
            1,
            "Иго",
            "Васи",
            "Влади",
            "23.04.2002",
            "Ул. Преображенская",
            "8928323892389",
            3232,
            "GOD",
            "Образовательная",
            540,
            "Директор",
            "",
            "",
            true,
            1,
            1,
            1,
            1,
        )
    )

}

@Preview
@Composable
fun SimpleCard() {
    val paddingModifier = Modifier.padding(20.dp)
    Card(
        elevation = 0.dp,
        modifier = paddingModifier
    ) {

        Surface(
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(0.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "Simple Card with elevation",
                modifier = Modifier.padding(0.dp)
            )
        }


    }
}





