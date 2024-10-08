package com.holoo.map.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.carto.styles.MarkerStyle
import com.carto.styles.MarkerStyleBuilder
import com.carto.styles.PolygonStyle
import com.carto.styles.PolygonStyleBuilder
import com.carto.utils.BitmapUtils
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Marker

@Composable
fun MainScreen() {
    val context = LocalContext.current

    var mapView by remember {
        mutableStateOf<MapView?>(null)
    }

    var marker by remember {
        mutableStateOf<Marker?>(null)
    }


    Scaffold { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            Box(
                Modifier
                    .fillMaxSize()
            ) {
                AndroidView(
                    factory = { context ->
                        val view = MapView(context)
                        view
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { map ->
                        mapView = map
                        marker?.let { mapView?.addMarker(marker) }
                    }
                )
            }

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        mapView?.cameraTargetPosition?.let {
                            marker?.let { mapView?.removeMarker(marker) }
                            val latLng = LatLng(it.latitude, it.longitude)
                            marker = createMarker(latLng, context)
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

// This method gets a LatLng as input and adds a marker on that position
fun createMarker(loc: LatLng, context: Context): Marker {
    val markStCr = MarkerStyleBuilder()
    markStCr.size = 30f
    val drawable = ResourcesCompat.getDrawable(
        context.resources,
        org.neshan.mapsdk.R.drawable.ic_cluster_marker_blue,
        null
    )
    val bitmap = drawableToBitmap(drawable!!)
    markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(bitmap)
    markStCr.setAnchorPoint(0f, 0f)
    // AnimationStyle object - that was created before - is used here
    val markSt: MarkerStyle = markStCr.buildStyle()

    // Creating marker
    return Marker(loc, markSt)
}


fun getPolygonStyle(): PolygonStyle? {
    val polygonStCr = PolygonStyleBuilder()
    return polygonStCr.buildStyle()
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val bitmap: Bitmap =
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}