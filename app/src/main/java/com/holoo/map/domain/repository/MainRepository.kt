package com.holoo.map.domain.repository

import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun saveLocationBookmark(locationBookmarkEntity: LocationBookmarkEntity)
    suspend fun getBookmarks(): Flow<List<LocationBookmarkEntity>>
}