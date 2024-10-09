package com.holoo.map.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationBookmark(locationBookmarkEntity: LocationBookmarkEntity)

    @Query("SELECT * FROM location_bookmark")
    fun getBookmarks(): Flow<List<LocationBookmarkEntity>>
}