package com.holoo.map.ui.components


import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.holoo.map.R
import com.holoo.map.ui.theme.HolooTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    title: Int,
    icon: Int? = null,
    fontSize: Int = 12,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() },
        elevation = ButtonDefaults.buttonElevation(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        if (icon != null) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = contentColor,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Text(
            text = stringResource(id = title),
            color = contentColor,
            fontSize = fontSize.sp,
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun PreviewCustomButton() {
    HolooTheme {
        CustomButton(
            title = R.string.confirm,
            onClick = {}
        )
    }
}