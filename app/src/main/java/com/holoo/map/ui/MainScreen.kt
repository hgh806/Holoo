package com.holoo.map.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.neshan.mapsdk.MapView

@Composable
fun MainScreen() {
    var mapView by remember {
        mutableStateOf<MapView?>(null)
    }


    Scaffold { paddings ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            AndroidView(
                factory = { context ->
                    val view = MapView(context)
                    view
                },
                modifier = Modifier.fillMaxSize(),
                update = { map ->
                    mapView = map
                }
            )
        }
    }
}