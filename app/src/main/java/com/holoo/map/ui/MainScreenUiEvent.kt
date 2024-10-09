package com.holoo.map.ui

import org.neshan.common.model.LatLng

sealed interface MainScreenUiEvent {
    data class OnAddMarker(val latLng: LatLng) : MainScreenUiEvent
    data class OnSaveMarker(val latLng: LatLng, val title: String, val description: String) : MainScreenUiEvent
}