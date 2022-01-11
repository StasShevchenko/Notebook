package com.example.notebook.feature_notebook.domain.repository

import androidx.room.Query
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.*
import com.example.notebook.feature_notebook.domain.util.SearchType
import kotlinx.coroutines.flow.Flow

interface NotebookRepository {


    //
    //Все необходимое для работы с информацией о людях
    //
    fun getEntries(searchType: SearchType): Flow<List<PeopleInfo>>

    suspend fun getEntryById(id: Int): People?

    suspend fun insertEntry(entry: People)

    suspend fun deleteEntry(entryId: Int)

    //
    //Все необходимое для работы с информацией об организациях
    //

    fun getOrganizations(): Flow<List<OrganizationInfo>>

    suspend fun getOrganizationTypes(): List<OrganizationType>

    suspend fun getOrganizationById(organizationId: Int): Organization

    suspend fun insertOrganization(organization: Organization)

    suspend fun deleteOrganization(organization: Organization)

    //
    //Все необходимое для работы с должностями
    //
    fun getPosts(): Flow<List<Post>>

    suspend fun insertPost(post: Post)

    suspend fun getPostById(postId: Int): Post

    suspend fun deletePost(post: Post)

    //
    //Отношения и родственники
    //

    suspend fun getRelations(): List<Relations>

    suspend fun getRelatives(): List<Relatives>
}