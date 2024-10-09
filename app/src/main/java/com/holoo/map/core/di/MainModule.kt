package com.holoo.map.core.di

import com.holoo.map.core.data.local.AppDatabase
import com.holoo.map.core.data.repository.MainRepositoryImp
import com.holoo.map.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(appDatabase: AppDatabase): MainRepository {
        return MainRepositoryImp(appDatabase.bookmarkDao())
    }
}