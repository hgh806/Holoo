package com.holoo.map.ui

import org.neshan.common.model.LatLng

sealed interface MainScreenUiEvent {
    data class OnAddMarker(val latLng: LatLng) : MainScreenUiEvent
    data object OnSaveMarker : MainScreenUiEvent
}