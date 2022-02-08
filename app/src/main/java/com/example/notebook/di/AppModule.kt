package com.example.notebook.di

import android.app.Application
import androidx.room.Room
import com.example.notebook.feature_notebook.data.data_source.NotebookDatabase
import com.example.notebook.feature_notebook.data.repository.NotebookRepositoryImpl
import com.example.notebook.feature_notebook.domain.repository.NotebookRepository
import com.example.notebook.feature_notebook.domain.use_case.entries_use_case.AddEntry
import com.example.notebook.feature_notebook.domain.use_case.entries_use_case.DeleteEntry
import com.example.notebook.feature_notebook.domain.use_case.entries_use_case.GetEntries
import com.example.notebook.feature_notebook.domain.use_case.entries_use_case.EntryUseCases
import com.example.notebook.feature_notebook.domain.use_case.familiar_type_use_case.GetFamiliarTypes
import com.example.notebook.feature_notebook.domain.use_case.organizations_use_case.*
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
    fun provideNotebookDatabase(app: Application): NotebookDatabase {
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
    fun provideNotebookRepository(db: NotebookDatabase): NotebookRepository {
        return NotebookRepositoryImpl(db.notebookDao)
    }

    @Provides
    @Singleton
    fun provideEntryUseCases(repository: NotebookRepository): EntryUseCases {
        return EntryUseCases(
            getEntries = GetEntries(repository),
            deleteEntry = DeleteEntry(repository),
            addEntry = AddEntry(repository)
        )
    }

    @Provides
    @Singleton
    fun provideOrganizationUseCases(repository: NotebookRepository): OrganizationUseCases {
        return OrganizationUseCases(
            addOrganization = AddOrganization(repository),
            deleteOrganization = DeleteOrganization(repository),
            getOrganizations = GetOrganizations(repository),
            getOrganizationTypes = GetOrganizationTypes(repository),
            getOrganization = GetOrganization(repository)
        )
    }

    @Provides
    @Singleton
    fun provideGetFamiliarTypesUseCase(repository: NotebookRepository): GetFamiliarTypes{
        return GetFamiliarTypes(repository)
    }
}