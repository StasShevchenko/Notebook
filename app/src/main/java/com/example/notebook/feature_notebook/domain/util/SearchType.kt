package com.example.notebook.feature_notebook.domain.util

sealed class SearchType(val searchQuery: String){
    class NameSearch(searchQuery: String): SearchType(searchQuery)
    class OrganizationSearch(searchQuery: String): SearchType(searchQuery)
    class PostSearch(searchQuery: String): SearchType(searchQuery)
}
