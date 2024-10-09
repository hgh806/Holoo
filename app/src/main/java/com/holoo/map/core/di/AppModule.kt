package com.holoo.map.core.di

import android.content.Context
import androidx.room.Room
import com.holoo.map.core.data.local.AppDatabase
import com.holoo.map.utils.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "holoo.db")
            .fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider {
        return LocationProvider(context)
    }
}