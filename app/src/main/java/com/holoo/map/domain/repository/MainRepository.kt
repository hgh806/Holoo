package com.holoo.map.domain.repository

import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import com.holoo.map.core.data.remote.response.DirectionResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MainRepository {
    suspend fun saveLocationBookmark(locationBookmarkEntity: LocationBookmarkEntity)
    suspend fun getBookmarks(): Flow<List<LocationBookmarkEntity>>
    suspend fun getDirection(origin: String, destination: String): Response<DirectionResponse>
}