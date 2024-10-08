package com.holoo.map.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holoo.map.R
import com.holoo.map.ui.theme.HolooTheme

@Composable
fun FullWidthButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int? = null,
    @DrawableRes icon: Int? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
) {
    FullWidthButtonTemplate(
        modifier = modifier,
        backgroundColor = containerColor,
        buttonContent = {
            text?.let {
                Text(
                    text = stringResource(id = text),
                    color = contentColor,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            icon?.let {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    tint = contentColor
                )
            }
        },
        onClick = onClick
    )
}

@Composable
fun FullWidthButtonTemplate(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    buttonContent: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Button(
        onClick = {
            focusManager.clearFocus()
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = true,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            buttonContent()
        }
    }
}

@Preview
@Composable
private fun PreviewFullWidthButton() {
    HolooTheme {
        FullWidthButton(
            text = R.string.request_permissions,
            onClick = {})
    }
}