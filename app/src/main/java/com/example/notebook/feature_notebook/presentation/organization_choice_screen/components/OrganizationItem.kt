package com.example.notebook.feature_notebook.presentation.organization_choice_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo

@Composable
fun OrganizationItem(
    item: OrganizationInfo,
    onOrganizationChoice: (Int) -> Unit,
    isChosen: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onOrganizationChoice(item.organizationId)
            },
        elevation = 4.dp,
        backgroundColor = if (isChosen)
            MaterialTheme.colors.primary
        else MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = item.organizationName,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Тип организации: ${item.organizationType}",
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Количество сотрудников: ${item.workersAmount}",
                modifier = Modifier.padding(4.dp)
            )
        }

    }
}