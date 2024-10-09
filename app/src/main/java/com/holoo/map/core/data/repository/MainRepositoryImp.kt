package com.holoo.map.core.data.repository

import com.holoo.map.BuildConfig
import com.holoo.map.core.data.local.dao.BookmarkDao
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import com.holoo.map.core.data.remote.api.DirectionApiService
import com.holoo.map.core.data.remote.response.DirectionResponse
import com.holoo.map.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val directionApiService: DirectionApiService,
): MainRepository {
    override suspend fun saveLocationBookmark(locationBookmarkEntity: LocationBookmarkEntity) {
        bookmarkDao.insertLocationBookmark(locationBookmarkEntity)
    }

    override suspend fun getBookmarks(): Flow<List<LocationBookmarkEntity>> {
        return bookmarkDao.getBookmarks()
    }

    override suspend fun getDirection(origin: String, destination: String): Response<DirectionResponse> {
        return directionApiService.getDirection(origin, destination, BuildConfig.API_KEY)
    }
}