package com.example.notebook.feature_notebook.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Relations(
    @PrimaryKey (autoGenerate = true)
    val familiarId: Int,
    val familiarType: String
)
