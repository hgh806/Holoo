package com.holoo.map.ui

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import com.holoo.map.domain.repository.MainRepository
import com.holoo.map.utils.LocationProvider
import com.holoo.map.utils.LocationProviderCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val mainRepository: MainRepository,
) :
    ViewModel(), LocationProviderCallback {
    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    init {
        locationProvider.setCallback(this)
    }

    fun onEvent(event: MainScreenUiEvent) = viewModelScope.launch {
        when (event) {
            is MainScreenUiEvent.OnAddMarker -> updateMarker(event.latLng)
            is MainScreenUiEvent.OnSaveMarker -> bookMarkLocation(event.latLng, event.title, event.description)
            is MainScreenUiEvent.OnRemoveBookmark -> TODO()
        }
    }

    private fun bookMarkLocation(latLng: LatLng, title: String, description: String) {
        viewModelScope.launch {
            val locationBookmarkEntity = LocationBookmarkEntity(
                id = 0,
                name = title,
                latitude = latLng.latitude,
                longitude = latLng.longitude,
                description = description,
            )
            mainRepository.saveLocationBookmark(locationBookmarkEntity)
        }
    }

    private fun updateMarker(latLng: LatLng) {
        _state.update {
            it.copy(marker = latLng)
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationProvider.removeCallback()
    }

    override fun onLocationChanged(location: Location) {
        _state.update {
            it.copy(currentLocation = LatLng(location.latitude, location.longitude))
        }
    }
}