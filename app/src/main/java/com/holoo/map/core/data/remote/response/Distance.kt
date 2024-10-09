package com.holoo.map.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Distance(
    val text: String,
    val value: Double
)