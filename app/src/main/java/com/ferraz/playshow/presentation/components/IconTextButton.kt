package com.ferraz.playshow.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.ferraz.playshow.presentation.theme.PlayShowTheme

@Suppress("LongParameterList")
@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    label: String,
    imageVector: ImageVector,
    backgroundColor: Color,
    tint: Color,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = label,
            tint = tint
        )

        Text(
            text = label,
            color = tint
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IconTextButtonPreview() {
    PlayShowTheme {
        IconTextButton(
            label = "Add to My List",
            imageVector = Icons.Outlined.Add,
            backgroundColor = MaterialTheme.colorScheme.secondary,
            tint = MaterialTheme.colorScheme.onSecondary,
            enabled = true,
            onClick = {}
        )
    }
}
