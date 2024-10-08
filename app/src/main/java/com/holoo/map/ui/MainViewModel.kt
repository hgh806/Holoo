package com.holoo.map.ui

import androidx.lifecycle.ViewModel
import com.holoo.map.utils.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val locationProvider: LocationProvider) : ViewModel() {
    init {
        locationProvider.setCallback()
    }

    override fun onCleared() {
        super.onCleared()
        locationProvider.removeCallback()
    }
}