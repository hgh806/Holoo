package com.holoo.map.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Leg(
    val distance: Distance,
    val duration: Duration,
    val steps: List<Step>,
    val summary: String
)