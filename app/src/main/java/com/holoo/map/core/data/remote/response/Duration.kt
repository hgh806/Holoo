package com.holoo.map.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Duration(
    val text: String,
    val value: Double
)