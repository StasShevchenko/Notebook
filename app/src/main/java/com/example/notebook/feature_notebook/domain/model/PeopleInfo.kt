package com.example.notebook.feature_notebook.domain.model

import com.example.notebook.feature_notebook.domain.model.entities.People
import kotlin.Exception

data class PeopleInfo(
    val peopleId: Int,
    val name: String,
    val secondName: String,
    val patronymic: String,
    val dateOfBirth: String,
    val address: String,
    val phoneNumber: String,
    val timestamp: Long,
    val organizationName: String,
    val organizationType: String,
    val workersAmount: Int,
    val postName: String,
    val familiarType: String,
    val relativeType: String,
    val favourite: Boolean,
    val organizationId: Int,
    val postId: Int,
    val relativeId: Int,
    val familiarId: Int,
)

fun PeopleInfo?.toPeople(): People? {
    if(this != null) {
        return People(
            this.peopleId,
            this.name,
            this.secondName,
            this.patronymic,
            this.dateOfBirth,
            this.address,
            this.phoneNumber,
            this.timestamp,
            this.organizationId,
            this.postId,
            this.relativeId,
            this.familiarId,
            this.favourite
        )
    } else return null
}

class InvalidEntryException(message: String): Exception(message)
