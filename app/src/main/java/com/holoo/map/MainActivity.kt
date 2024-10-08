package com.holoo.map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.holoo.map.ui.MainScreen
import com.holoo.map.ui.MainViewModel
import com.holoo.map.ui.permission.RequiredAppPermissions
import com.holoo.map.ui.theme.HolooTheme
import com.holoo.map.utils.LocationProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val permissionState = rememberMultiplePermissionsState(LocationProvider.permissionList)

            HolooTheme {
                if (permissionState.allPermissionsGranted.not()) {
                    RequiredAppPermissions(permissionState = permissionState)
                } else {
                    MainScreen()
                }
            }
        }
    }
}