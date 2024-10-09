package com.holoo.map.core.data.remote.response

data class Step(
    val bearing_after: Int,
    val distance: Distance,
    val duration: Duration,
    val exit: Int,
    val instruction: String,
    val modifier: String,
    val name: String,
    val polyline: String,
    val start_location: List<Double>,
    val type: String
)