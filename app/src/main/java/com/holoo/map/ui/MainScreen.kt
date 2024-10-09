package com.holoo.map.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.carto.styles.MarkerStyle
import com.carto.styles.MarkerStyleBuilder
import com.carto.styles.PolygonStyle
import com.carto.styles.PolygonStyleBuilder
import com.carto.utils.BitmapUtils
import com.holoo.map.R
import com.holoo.map.ui.dialog.LocationInputDialog
import com.holoo.map.ui.theme.HolooTheme
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current

    var mapView by remember {
        mutableStateOf<MapView?>(null)
    }

    var marker by remember {
        mutableStateOf<Marker?>(null)
    }

    var showInputDialog by remember {
        mutableStateOf(false)
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
            skipHiddenState = false
        )
    )

    LaunchedEffect(key1 = marker) {
        if (marker == null)
            scaffoldState.bottomSheetState.hide()
        else {
            scaffoldState.bottomSheetState.show()
            scaffoldState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        containerColor = Color.Transparent,
        sheetContainerColor = Color.Transparent,
        sheetShadowElevation = 0.dp,
        sheetSwipeEnabled = false,
        sheetTonalElevation = 0.dp,
        sheetContent = {
            SheetContent(direction = {}, saveLocation = {})
        }
    ) {
        Box(modifier = Modifier.padding()) {
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

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding(),
                onClick = {
                    showInputDialog = true
                }
            ) {
                Row(
                    Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                    Text(
                        stringResource(R.string.input_destination),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        if (showInputDialog) {
            LocationInputDialog(
                latLng = marker?.latLng,
                confirm = { latLng ->
                    marker?.let { mapView?.removeMarker(marker) }
                    marker = createMarker(latLng, context)
                    showInputDialog = false
                },
                dismiss = { showInputDialog = false }
            )
        }
    }
}

@Composable
fun SheetContent(
    direction: () -> Unit,
    saveLocation: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .height(72.dp)
            .fillMaxWidth()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = direction,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_directions_24),
                    contentDescription = stringResource(R.string.direction_button)
                )

                Text(stringResource(R.string.direction))
            }

            Button(
                onClick = saveLocation,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_bookmark_border_24),
                    contentDescription = stringResource(R.string.save_button)
                )

                Text(stringResource(R.string.save))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    HolooTheme {
        MainScreen()
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