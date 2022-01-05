package com.example.notebook.feature_notebook.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrganizationType(
    @PrimaryKey(autoGenerate = true)
    val typeId: Int,
    val organizationType: String,
)
