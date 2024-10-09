package com.holoo.map.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class DirectionResponse(
    val routes: List<Route>
)