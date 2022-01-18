package com.example.notebook.feature_notebook.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class People(
    @PrimaryKey (autoGenerate = true)
    val peopleId: Int? = null,
    val name: String,
    val secondName: String,
    val patronymic: String,
    val dateOfBirth: String,
    val address: String,
    val phoneNumber: String,
    val timestamp: Long,
    val organizationId: Int,
    val postId: Int,
    val relativeId: Int,
    val familiarId: Int,
    val favourite: Boolean = false
)
