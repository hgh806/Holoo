package com.holoo.map.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_bookmark")
data class LocationBookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val description: String
)
