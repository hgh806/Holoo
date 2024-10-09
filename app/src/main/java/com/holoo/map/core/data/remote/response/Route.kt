package com.holoo.map.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Route(
    val legs: List<Leg>,
    val overview_polyline: OverviewPolyline
)