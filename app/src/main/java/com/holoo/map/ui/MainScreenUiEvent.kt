package com.holoo.map.ui

import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import org.neshan.common.model.LatLng

sealed interface MainScreenUiEvent {
    data class OnAddMarker(val latLng: LatLng) : MainScreenUiEvent
    data class GetDirection(val origin: LatLng, val destination: LatLng) : MainScreenUiEvent
    data class OnSaveMarker(val latLng: LatLng, val title: String, val description: String) : MainScreenUiEvent
    data class OnRemoveBookmark(val bookmark: LocationBookmarkEntity) : MainScreenUiEvent
}