package com.example.notebook.di

import android.app.Application
import androidx.room.Room
import com.example.notebook.feature_notebook.data.data_source.NotebookDatabase
import com.example.notebook.feature_notebook.data.repository.NotebookRepositoryImpl
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun  provideNotebookDatabase (app: Application): NotebookDatabase {
        return Room.databaseBuilder(
            app,
            NotebookDatabase::class.java,
            NotebookDatabase.DATABASE_NAME
        )
            .createFromAsset("database/entries_db.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideNotebookRepository(db: NotebookDatabase): NotebookRepository{
        return NotebookRepositoryImpl(db.notebookDao)
    }
}