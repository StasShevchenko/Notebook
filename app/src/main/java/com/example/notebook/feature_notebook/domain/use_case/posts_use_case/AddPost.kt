package com.example.notebook.feature_notebook.domain.use_case.posts_use_case

import com.example.notebook.feature_notebook.domain.model.entities.Post
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository

class AddPost(
    private val repository: NotebookRepository
) {
    suspend operator fun invoke(post: Post): Long {
       return repository.insertPost(post)
    }
}