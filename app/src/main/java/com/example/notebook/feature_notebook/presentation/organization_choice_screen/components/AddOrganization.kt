package com.example.notebook.feature_notebook.presentation.organization_choice_screen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.domain.model.entities.OrganizationType
import com.example.notebook.feature_notebook.domain.use_case.organizations_use_case.AddOrganization
import com.example.notebook.feature_notebook.presentation.util.OutlinedErrorTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddOrganization(
    organizationTypes: List<OrganizationType>,
    organizationName: String,
    onOrganizationNameChanged: (String) -> Unit,
    workersAmount: Int,
    onWorkersAmountChanged: (Int) -> Unit,
    onOrganizationSave: () -> Unit,
    chosenOrganizationType: OrganizationType?,
    onOrganizationTypeChoice: (OrganizationType) -> Unit,
    nameFieldErrorStatus: Boolean,
    nameFieldErrorMessage: String,
    workersAmountFieldErrorStatus: Boolean,
    workersAmountFieldErrorMessage: String
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var showAddOrganization by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (showAddOrganization) 180f else 0f)

    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Text(
                    text = "Добавить организацию",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(6f),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier
                        .size(35.dp)
                        .alpha(ContentAlpha.medium),
                    onClick = {
                        showAddOrganization = !showAddOrganization
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "",
                        modifier = Modifier.rotate(rotationState)
                    )
                }

            }
            if (showAddOrganization) {
                OutlinedErrorTextField(
                    value = organizationName,
                    onValueChange = {
                        onOrganizationNameChanged(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = {
                        Text("Название организации")
                    },
                    isError = nameFieldErrorStatus,
                    errorMessage = nameFieldErrorMessage,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedErrorTextField(
                    value = if (workersAmount != 0) workersAmount.toString() else "",
                    onValueChange = {
                        onWorkersAmountChanged(if (it == "") 0 else it.toInt())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = {
                        Text("Количество сотрудников")
                    },
                    isError = workersAmountFieldErrorStatus,
                    errorMessage = workersAmountFieldErrorMessage,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(4.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 8.dp)
                ) {
                    organizationTypes.forEach {
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = it.typeId == chosenOrganizationType?.typeId,
                                onClick = {
                                    onOrganizationTypeChoice(it)
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(it.organizationType)
                        }
                    }
                    Button(
                        modifier = Modifier.padding(4.dp),
                        onClick = {
                            onOrganizationSave()
                        }
                    ) {
                        Text("Сохранить")
                    }
                }

            }
        }
    }

}