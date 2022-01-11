package com.example.notebook.feature_notebook.presentation.entries.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_notebook.domain.util.SearchType

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchType: SearchType = SearchType.NameSearch(searchQuery),
    onSearchChange: (SearchType) -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 8.dp, bottom = 8.dp)
            .fillMaxWidth(1f)
    ){
        Text("Искать по:")
        DefaultRadioButton(
            text = "Фио",
            selected = searchType is SearchType.NameSearch,
            onSelect = { onSearchChange(SearchType.NameSearch(searchQuery)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        DefaultRadioButton(
            text = "Организации",
            selected = searchType is SearchType.OrganizationSearch,
            onSelect = { onSearchChange(SearchType.OrganizationSearch(searchQuery)) }
        )
        DefaultRadioButton(
            text = "Должности",
            selected = searchType is SearchType.PostSearch,
            onSelect = { onSearchChange(SearchType.PostSearch(searchQuery)) }
        )
    }

}

@Preview
@Composable
fun SearchPreviewSection(){
    SearchSection(searchQuery = "Игорь", onSearchChange = {})
}