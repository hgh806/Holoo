package com.holoo.map.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Step(
    val bearing_after: Int,
    val distance: Distance,
    val duration: Duration,
    val instruction: String,
    val name: String,
    val polyline: String,
    val start_location: List<Double>,
    val type: String
)