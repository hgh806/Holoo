package com.holoo.map.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.holoo.map.R
import com.holoo.map.ui.components.CustomButton
import com.holoo.map.ui.components.Spacer24
import com.holoo.map.ui.components.Spacer32
import com.holoo.map.ui.components.Spacer8
import com.holoo.map.ui.theme.HolooTheme
import org.neshan.common.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationInputDialog(
    latLng: LatLng?,
    dismiss: () -> Unit,
    confirm: (LatLng) -> Unit,
) {
    var lat by remember {
        mutableStateOf(latLng?.latitude?.toString() ?: "")
    }

    var lon by remember {
        mutableStateOf(latLng?.longitude?.toString() ?: "")
    }

    Dialog(
        onDismissRequest = dismiss, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.input_destination),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                )

                Spacer8()

                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = lat,
                    onValueChange = { lat = it },
                    label = { Text(text = stringResource(id = R.string.latitude)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        if (lat.isNotBlank())
                            IconButton(onClick = { lat = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = ""
                                )
                            }
                    }
                )

                Spacer8()

                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = lon,
                    onValueChange = { lon = it },
                    label = { Text(text = stringResource(id = R.string.longitude)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        if (lon.isNotBlank())
                            IconButton(onClick = { lon = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = ""
                                )
                            }
                    }
                )

                Spacer24()

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    CustomButton(
                        title = R.string.confirm,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .weight(0.2f)
                            .height(38.dp),
                        fontSize = 14,
                        onClick = {
                            confirm(LatLng(lat.toDouble(), lon.toDouble()))
                        }
                    )

                    Spacer24()

                    CustomButton(
                        title = R.string.cancel,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14,
                        modifier = Modifier
                            .weight(0.2f)
                            .height(38.dp),
                        onClick = dismiss
                    )
                }
            }

            Spacer32()
        }
    }
}

@Preview
@Composable
fun PreviewConfirmDialog() {
    HolooTheme {
        LocationInputDialog(
            latLng = null,
            dismiss = {},
            confirm = {},
        )
    }
}