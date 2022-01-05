package com.example.notebook.feature_notebook.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notebook.feature_notebook.domain.model.entities.*


@Database(
    entities = [Organization::class, OrganizationType::class, People::class,
        Post::class, Relations::class, Relatives::class],
    version = 1,
    exportSchema = true
)
abstract class NotebookDatabase : RoomDatabase(){

    abstract val notebookDao: NotebookDao

    companion object{
        const val DATABASE_NAME = "entries_db"
    }
}