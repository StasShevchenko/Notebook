package com.example.notebook.feature_notebook.presentation.add_edit_entry.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.presentation.util.OutlinedErrorTextField
import com.google.accompanist.insets.LocalWindowInsets
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun ReadonlyTextField(
    value: String,
    errorStatus: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    val relocationRequester = remember { BringIntoViewRequester() }
    val ime = LocalWindowInsets.current.ime
    val scope = rememberCoroutineScope()


    Box {
        OutlinedErrorTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        scope.launch {
                            delay(2000)
                            relocationRequester.bringIntoView()
                        }

                    }
                },
            label = label,
            isError = errorStatus,
            errorMessage = errorMessage
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}