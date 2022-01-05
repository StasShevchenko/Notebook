package com.example.notebook.feature_notebook.data.data_source

import android.provider.ContactsContract
import androidx.room.*
import com.example.notebook.feature_notebook.domain.model.OrganizationInfo
import com.example.notebook.feature_notebook.domain.model.PeopleInfo
import com.example.notebook.feature_notebook.domain.model.entities.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NotebookDao {

    //
    //Все необходимое для работы с информацией о людях
    //
    @Query("SELECT peopleId, name, secondName, patronymic, dateOfBirth, address, phoneNumber, timestamp, organizationName, organizationType, workersAmount, postName, familiarType, relativeType, favourite\n" +
            "FROM people\n" +
            " JOIN (SELECT organizationId, organizationName, workersAmount, organizationType FROM Organization JOIN OrganizationType USING(typeId)) USING (organizationId)\n" +
            " JOIN Post USING (postId)\n" +
            " JOIN RELATIONS USING (familiarId)\n" +
            " JOIN Relatives USING(relativeId)\n")
    fun getEntries(): Flow<List<PeopleInfo>>

    @Query("SELECT * FROM people WHERE peopleId = :id")
    suspend fun getEntryById(id: Int): People?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: People)

    @Query("DELETE FROM People WHERE peopleId = :entryId")
    suspend fun deleteEntry(entryId: Int)

    //
    //Все необходимое для работы с информацией об организациях
    //
    @Query("SELECT organizationId, organizationName, workersAmount, organizationType"+
        "FROM People JOIN OrganizationType USING(typeId)")
    fun getOrganizations(): Flow<List<OrganizationInfo>>

    @Query("SELECT * FROM organizationtype")
    suspend fun getOrganizationTypes(): List<OrganizationType>

    @Query("SELECT * FROM Organization WHERE organizationId = :organizationId")
    suspend fun getOrganizationById(organizationId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrganization(organization: Organization)

    @Delete
    suspend fun deleteOrganization(organization: Organization)

    //
    //Все необходимое для работы с должностями
    //
    @Query("SELECT * FROM Post")
    fun getPosts(): Flow<List<Post>>

    @Insert
    suspend fun insertPost(post: Post)

    @Query("SELECT * FROM Post WHERE postId = :postId")
    suspend fun getPostById(postId: Int)

    @Delete
    suspend fun deletePost(post: Post)

    //
    //Получение списков родственных и дружеских связей
    //
    @Query("SELECT * FROM Relations")
    suspend fun getRelations(): List<Relations>

    @Query("SELECT * FROM Relatives")
    suspend fun getRelatives(): List<Relatives>


}