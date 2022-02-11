package com.example.notebook.feature_notebook.domain.use_case.posts_use_case

data class PostsUseCases(
    val addPost: AddPost,
    val deletePost: DeletePost,
    val getPosts: GetPosts
)