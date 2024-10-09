package com.holoo.map.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class OverviewPolyline(
    val points: String
)