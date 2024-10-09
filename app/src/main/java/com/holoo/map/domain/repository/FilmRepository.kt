package com.holoo.map.domain.repository

import com.holoo.map.core.data.local.entity.LocationBookmarkEntity

interface MainRepository {
    suspend fun saveLocationBookmark(locationBookmarkEntity: LocationBookmarkEntity)
}