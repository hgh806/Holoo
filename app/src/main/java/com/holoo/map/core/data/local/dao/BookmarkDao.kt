package com.holoo.map.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationBookmark(locationBookmarkEntity: LocationBookmarkEntity)
}