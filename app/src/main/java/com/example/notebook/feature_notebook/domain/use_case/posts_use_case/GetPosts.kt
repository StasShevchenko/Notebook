package com.example.notebook.feature_notebook.domain.use_case.posts_use_case

import com.example.notebook.feature_notebook.domain.model.entities.People
import com.example.notebook.feature_notebook.domain.model.entities.Post
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import kotlinx.coroutines.flow.Flow

class GetPosts(
    private val repository: NotebookRepository
) {
    operator fun invoke(searchQuery: String): Flow<List<Post>> {
        return repository.getPosts(searchQuery)
    }
}