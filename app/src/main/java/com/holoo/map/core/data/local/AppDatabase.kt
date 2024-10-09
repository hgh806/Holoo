package com.holoo.map.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.holoo.map.core.data.local.dao.BookmarkDao
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity

@Database(
    version = 2,
    exportSchema = true,
    entities = [
        LocationBookmarkEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}