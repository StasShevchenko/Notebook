package com.example.notebook.feature_notebook.domain.model

data class PeopleInfo(
    val peopleId: Int,
    val name: String,
    val secondName: String,
    val patronymic: String,
    val dataOfBirth: String,
    val address: String,
    val phoneNumber: String,
    val timestamp: Long,
    val organizationName: String,
    val organizationType: String,
    val workersAmount: Int,
    val postName: String,
    val familiarType: String,
    val relativeType: String,
    val favourite: Boolean
)
