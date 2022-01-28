package com.example.notebook.feature_notebook.data.repository

import com.example.notebook.feature_notebook.data.data_source.NotebookDao
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.*
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import com.example.notebook.feature_notebook.domain.util.SearchType
import kotlinx.coroutines.flow.Flow

class NotebookRepositoryImpl(
    private val dao: NotebookDao
) : NotebookRepository {
    override fun getEntries(searchType: SearchType): Flow<List<PeopleInfo>> {
        return dao.getEntries(searchType)
    }

    override suspend fun getEntryById(id: Int): People? {
        return dao.getEntryById(id)
    }

    override suspend fun insertEntry(entry: People) {
        dao.insertEntry(entry)
    }

    override suspend fun deleteEntry(entryId: Int) {
        dao.deleteEntry(entryId)
    }

    override fun getOrganizations(searchQuery: String): Flow<List<OrganizationInfo>> {
        return dao.getOrganizations(searchQuery)
    }

    override suspend fun getOrganizationTypes(): List<OrganizationType> {
        return dao.getOrganizationTypes()
    }

    override suspend fun getOrganizationById(organizationId: Int): Organization {
        return dao.getOrganizationById(organizationId)
    }

    override suspend fun insertOrganization(organization: Organization) {
        dao.insertOrganization(organization)
    }

    override suspend fun deleteOrganization(organization: Organization) {
        dao.deleteOrganization(organization)
    }

    override fun getPosts(): Flow<List<Post>> {
        return dao.getPosts()
    }

    override suspend fun insertPost(post: Post) {
        dao.insertPost(post)
    }

    override suspend fun getPostById(postId: Int): Post {
        return dao.getPostById(postId)
    }

    override suspend fun deletePost(post: Post) {
        dao.deletePost(post)
    }

    override suspend fun getRelations(): List<Relations> {
        return dao.getRelations()
    }

    override suspend fun getRelatives(): List<Relatives> {
        return dao.getRelatives()
    }
}
