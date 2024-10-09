package com.holoo.map.core.di

import com.holoo.map.core.data.local.AppDatabase
import com.holoo.map.core.data.remote.api.DirectionApiService
import com.holoo.map.core.data.repository.MainRepositoryImp
import com.holoo.map.domain.repository.MainRepository
import com.holoo.map.domain.use_cases.GetDirectionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(appDatabase: AppDatabase, directionApiService: DirectionApiService): MainRepository {
        return MainRepositoryImp(appDatabase.bookmarkDao(), directionApiService)
    }

    @Provides
    @ViewModelScoped
    fun provideDirectionApiService(retrofit: Retrofit): DirectionApiService {
        return retrofit.create(DirectionApiService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDirectionUseCase(mainRepository: MainRepository): GetDirectionUseCase {
        return GetDirectionUseCase(mainRepository)
    }
}