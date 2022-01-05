package com.example.notebook.feature_notebook.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Organization(
    @PrimaryKey(autoGenerate = true)
    val organizationId: Int,
    val organizationName: String,
    val workersAmount: Int,
    val typeId: Int
)
