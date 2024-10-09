package com.holoo.map.ui

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MainViewModel @Inject constructor(private val locationProvider: LocationProvider) :
    ViewModel(), LocationProviderCallback {
    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    init {
        locationProvider.setCallback(this)
    }

    fun onEvent(event: MainScreenUiEvent) = viewModelScope.launch {
        when(event) {
            is MainScreenUiEvent.OnAddMarker -> updateMarker(event.latLng)
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
            it.copy(currentLocation = LatLng(location.latitude, location.longitude),)
        }
    }
}