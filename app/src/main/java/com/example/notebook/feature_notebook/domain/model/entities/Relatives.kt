package com.example.notebook.feature_notebook.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Relatives(
    @PrimaryKey (autoGenerate = true)
    val relativeId: Int,
    val relativeType: String
)
