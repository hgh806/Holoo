package com.holoo.map.core.data.remote.response

data class Route(
    val legs: List<Leg>,
    val overview_polyline: OverviewPolyline
)