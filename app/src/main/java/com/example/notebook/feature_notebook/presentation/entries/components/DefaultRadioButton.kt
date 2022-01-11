package com.example.notebook.feature_notebook.presentation.entries.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
    RadioButton(
        selected = selected,
        onClick = onSelect,
        modifier = Modifier.size(20.dp).padding(4.dp),
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colors.primary,
            unselectedColor = MaterialTheme.colors.onBackground
        )
    )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Preview
@Composable
fun RadioBtnPreview(){
    DefaultRadioButton(text = "firstButton", selected = true, onSelect = { })
}