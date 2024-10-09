package com.holoo.map.ui

import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
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
import com.holoo.map.R
import com.holoo.map.ui.dialog.BookmarkDialog
import com.holoo.map.ui.dialog.BookmarkListDialog
import com.holoo.map.ui.dialog.LocationInputDialog
import com.holoo.map.ui.theme.HolooTheme
import com.holoo.map.utils.MapUtils
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Marker
import org.neshan.mapsdk.model.Polyline

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: MainUiState,
    onEvent: (event: MainScreenUiEvent) -> Unit,
) {
    val context = LocalContext.current

    var mapView by remember {
        mutableStateOf<MapView?>(null)
    }

    var marker by remember {
        mutableStateOf<Marker?>(null)
    }

    var userMarker by remember {
        mutableStateOf<Marker?>(null)
    }

    var polyLine by remember {
        mutableStateOf<Polyline?>(null)
    }

    var showInputDialog by remember {
        mutableStateOf(false)
    }

    var showBookmarkDialog by remember {
        mutableStateOf(false)
    }

    var showBookmarkList by remember {
        mutableStateOf(false)
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
            skipHiddenState = false
        )
    )

    //show bottomSheet when a marker added
    LaunchedEffect(key1 = marker) {
        if (marker == null)
            scaffoldState.bottomSheetState.hide()
        else {
            scaffoldState.bottomSheetState.show()
            scaffoldState.bottomSheetState.expand()
        }
    }

    //show marker when we have marker
    LaunchedEffect(uiState.marker) {
        uiState.marker?.let {
            marker?.let { oldMarker ->
                mapView?.removeMarker(oldMarker)
            }

            marker = MapUtils.createMarker(
                loc = it,
                context = context,
            )
            mapView?.addMarker(marker)
            mapView?.moveCamera(marker!!.latLng, .25f)
        }
    }

    //show direction if we have routes
    LaunchedEffect(uiState.routes) {
        if (uiState.routes.isNotEmpty()) {
            if (polyLine != null)
                mapView?.removePolyline(polyLine)

            polyLine = Polyline(ArrayList(uiState.routes), MapUtils.getLineStyle())
            mapView?.addPolyline(polyLine)
            mapView?.moveCamera(uiState.routes.first(), .25f)
        }
    }

    //update current location when it changes
    LaunchedEffect(key1 = uiState.currentLocation) {
        if (userMarker != null)
            mapView?.removeMarker(userMarker)

        uiState.currentLocation?.let { currentLocation ->
            userMarker = MapUtils.createMarker(
                loc = currentLocation,
                context = context,
                icon = org.neshan.mapsdk.R.drawable.ic_marker,
                iconSize = 20f
            )

            mapView?.addMarker(userMarker)
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
            SheetContent(
                direction = {
                    if (userMarker == null) {
                        Toast.makeText(context, R.string.please_enable_gps, Toast.LENGTH_SHORT).show()
                    } else if (marker == null) {
                        Toast.makeText(context, R.string.please_select_destination, Toast.LENGTH_SHORT).show()
                    } else {
                        onEvent(MainScreenUiEvent.GetDirection(origin = userMarker!!.latLng, destination = marker!!.latLng))
                    }
                },
                saveLocation = {
                    showBookmarkDialog = true
                }
            )
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
                            val latLng = LatLng(it.latitude, it.longitude)
                            onEvent(MainScreenUiEvent.OnAddMarker(latLng))
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

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .padding(top = 12.dp)
                    .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape),
                onClick = { showBookmarkList = true },
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_bookmark_border_24),
                    contentDescription = stringResource(R.string.show_bookmarks)
                )
            }
        }

        if (showInputDialog) {
            LocationInputDialog(
                latLng = marker?.latLng,
                confirm = { latLng ->
                    onEvent(MainScreenUiEvent.OnAddMarker(latLng))
                    showInputDialog = false
                },
                dismiss = { showInputDialog = false }
            )
        }

        if (showBookmarkDialog) {
            BookmarkDialog(
                confirm = { title, description ->
                    showBookmarkDialog = false
                    onEvent(MainScreenUiEvent.OnSaveMarker(marker?.latLng!!, title, description))
                },
                dismiss = {
                    showBookmarkDialog = false
                }
            )
        }

        if (showBookmarkList) {
            BookmarkListDialog(
                bookmarks = uiState.bookmarks,
                dismiss = {
                    showBookmarkList = false
                },
                showOnMap = { bookmark ->
                    val latLng = LatLng(bookmark.latitude, bookmark.longitude)
                    onEvent(MainScreenUiEvent.OnAddMarker(latLng))
                    showBookmarkList = false
                },
                remove = { bookmark ->
                    onEvent(MainScreenUiEvent.OnRemoveBookmark(bookmark))
                }
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
        MainScreen(
            uiState = MainUiState(),
            onEvent = {}
        )
    }
}