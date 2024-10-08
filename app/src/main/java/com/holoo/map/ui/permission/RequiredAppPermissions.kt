package com.holoo.map.ui.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.holoo.map.R
import com.holoo.map.ui.components.FullWidthButton
import com.holoo.map.utils.extension.openPermissionSetting

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequiredAppPermissions(permissionState: MultiplePermissionsState) {
    val context = LocalContext.current

    val textToShow = if (permissionState.shouldShowRationale) {
        stringResource(R.string.grant_permission_please)
    } else {
        stringResource(R.string.grant_permission_from_mobile_settings)
    }

    Content(
        textToShow = textToShow,
        shouldShowRationale = permissionState.shouldShowRationale,
        launchPermissions = { permissionState.launchMultiplePermissionRequest() },
        openSettings = { context.openPermissionSetting() }
    )
}

@Composable
private fun Content(
    textToShow: String,
    shouldShowRationale: Boolean,
    launchPermissions: () -> Unit,
    openSettings: () -> Unit
    ) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(.8f),
                text = textToShow,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(100.dp))

            FullWidthButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = {
                    if (shouldShowRationale) {
                        openSettings()
                    } else {
                        launchPermissions()
                    }
                },
                text = R.string.request_permissions
            )
        }
    }
}