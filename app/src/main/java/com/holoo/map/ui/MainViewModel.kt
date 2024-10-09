package com.holoo.map.ui

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borna.dotinfilm.core.data.remote.adapter.Failure
import com.borna.dotinfilm.core.data.remote.adapter.Success
import com.holoo.map.core.data.local.entity.LocationBookmarkEntity
import com.holoo.map.domain.repository.MainRepository
import com.holoo.map.domain.use_cases.GetDirectionUseCase
import com.holoo.map.utils.LocationProvider
import com.holoo.map.utils.LocationProviderCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
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
    private val getDirectionUseCase: GetDirectionUseCase,
) : ViewModel(), LocationProviderCallback {
    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    init {
        locationProvider.setCallback(this)
        getBookmarks()
    }

    private fun getBookmarks() = viewModelScope.launch(IO) {
        mainRepository.getBookmarks().collect { bookmarks ->
            _state.update {
                it.copy(bookmarks = bookmarks)
            }
        }
    }

    fun onEvent(event: MainScreenUiEvent) = viewModelScope.launch {
        when (event) {
            is MainScreenUiEvent.OnAddMarker -> updateMarker(event.latLng)
            is MainScreenUiEvent.OnSaveMarker -> bookMarkLocation(
                event.latLng,
                event.title,
                event.description
            )

            is MainScreenUiEvent.OnRemoveBookmark -> removeBookmark(event.bookmark)
            is MainScreenUiEvent.GetDirection -> getDirection(event.origin, event.destination)
        }
    }

    private fun removeBookmark(bookmark: LocationBookmarkEntity) {
        viewModelScope.launch(IO) {
            mainRepository.removeBookmark(bookmark)
        }
    }

    private fun bookMarkLocation(latLng: LatLng, title: String, description: String) {
        viewModelScope.launch(IO) {
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

    private fun getDirection(origin: LatLng, destination: LatLng) {
        viewModelScope.launch {
            getDirectionUseCase(origin, destination).collect { result ->
                when (result) {
                    is Failure -> _state.update {
                        it.copy(
                            error = result.error,
                            routes = emptyList()
                        )
                    }

                    is Success -> _state.update {
                        it.copy(
                            routes = result.value
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationProvider.removeCallback()
    }

    override fun onLocationChanged(location: Location?) {
        _state.update {
            val latLng = if (location != null) LatLng(location.latitude, location.longitude) else null
            it.copy(currentLocation = latLng)
        }
    }
}