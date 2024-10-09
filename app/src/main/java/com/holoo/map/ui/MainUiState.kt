package com.holoo.map.ui

import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import org.neshan.common.model.LatLng

data class MainUiState(
    val currentLocation: LatLng? = null,
    val marker: LatLng? = null,
    val bookmarks: List<LocationBookmarkEntity> = emptyList()
)
