package com.holoo.map.core.data.remote.mapper

import com.holoo.map.core.data.remote.response.Route
import org.neshan.common.model.LatLng
import org.neshan.common.utils.PolylineEncoding

fun Route.toDirection(): List<LatLng> {
    val polylineList = this.legs.flatMap { it.steps.map { it.polyline } }
    val encodedList = polylineList.map { PolylineEncoding.decode(it) }.flatten()
    return encodedList
}