package com.holoo.map.core.data.repository

import com.holoo.map.core.data.local.dao.BookmarkDao
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import com.holoo.map.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(
    private val bookmarkDao: BookmarkDao
): MainRepository {
    override suspend fun saveLocationBookmark(locationBookmarkEntity: LocationBookmarkEntity) {
        bookmarkDao.insertLocationBookmark(locationBookmarkEntity)
    }

    override suspend fun getBookmarks(): Flow<List<LocationBookmarkEntity>> {
        return bookmarkDao.getBookmarks()
    }
}