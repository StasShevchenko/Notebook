package com.example.notebook.feature_notebook.presentation.entries.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.domain.util.EntryOrder
import com.example.notebook.feature_notebook.domain.util.OrderType
import com.example.notebook.feature_notebook.domain.util.SearchType

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun SearchBar(
    searchQuery: String = "",
    searchType: SearchType = SearchType.NameSearch(searchQuery),
    onSearchChanged: (SearchType) -> Unit,
    onOrderChanged: (EntryOrder) -> Unit,
    orderType: EntryOrder = EntryOrder.Alphabet(OrderType.Descending),
    expandedState: Boolean = false,
    onExpandedStateChanged: (Boolean) -> Unit
) {

    var showOrderMenu by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp
        ) {
            Column(
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                        value = searchQuery,
                        onValueChange = {
                            onSearchChanged(searchType.copy(it))
                        },

                        label = {
                            Text(text = "Поиск")
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, "")
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme
                                .colors.onSurface
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }),
                        singleLine = true

                    )
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = {
                            showOrderMenu = !showOrderMenu
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = ""
                        )
                    }
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        DropdownMenu(
                            expanded = showOrderMenu,
                            onDismissRequest = { showOrderMenu = false },

                            ) {
                            DropdownMenuItem(
                                onClick = {
                                    if (orderType.orderType is OrderType.Ascending) onOrderChanged(
                                        orderType.copy(OrderType.Descending)
                                    )
                                    else onOrderChanged(orderType.copy(OrderType.Ascending))
                                }

                            ) {
                                Text(
                                    "Сортировать по ${if (orderType.orderType is OrderType.Ascending) "убыванию" else "возрастанию"}",
                                    textAlign = TextAlign.Left
                                )
                            }
                            DropdownMenuItem(
                                modifier = Modifier.wrapContentSize(unbounded = true),
                                onClick = {
                                    if (orderType is EntryOrder.Date) onOrderChanged(
                                        EntryOrder.Alphabet(
                                            orderType.orderType
                                        )
                                    )
                                    else onOrderChanged(EntryOrder.Date(orderType.orderType))
                                }
                            ) {
                                Text(
                                    "Сортировать по ${if (orderType is EntryOrder.Alphabet) "времени создания" else "алфавиту"}",
                                    textAlign = TextAlign.Left
                                )
                            }
                        }
                    }
                }

                Row(
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(35.dp)
                            .alpha(ContentAlpha.medium),
                        onClick = {
                            onExpandedStateChanged(expandedState)
                        },

                        ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "",
                            modifier = Modifier.rotate(rotationState)
                        )

                    }
                }
                if (expandedState) {

                    SearchSection(
                        searchType = searchType,
                        searchQuery = searchQuery,
                        onSearchChange = {
                            onSearchChanged(it)
                        }
                    )


                }
            }

        }
    }



