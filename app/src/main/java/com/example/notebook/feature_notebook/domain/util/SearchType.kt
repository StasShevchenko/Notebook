package com.example.notebook.feature_notebook.domain.util


sealed class SearchType(val searchQuery: String){
    class NameSearch(searchQuery: String): SearchType(searchQuery)
    class OrganizationSearch(searchQuery: String): SearchType(searchQuery)
    class PostSearch(searchQuery: String): SearchType(searchQuery)

    fun copy(searchQuery: String): SearchType {
        return when (this) {
            is NameSearch -> NameSearch(searchQuery)
            is OrganizationSearch -> OrganizationSearch(searchQuery)
            is PostSearch -> PostSearch(searchQuery)
        }
    }
}
