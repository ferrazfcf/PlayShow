package com.ferraz.playshow.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferraz.playshow.presentation.theme.PlayShowTheme

@Composable
fun PulsatingIcon(
    modifier: Modifier = Modifier,
    label: String,
    imageVector: ImageVector,
    tint: Color,
    pulseFraction: Float = 1.2f
) {
    val infiniteTransition = rememberInfiniteTransition(label)
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        label = label,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = modifier) {
        Icon(
            imageVector = imageVector,
            contentDescription = label,
            tint = tint,
            modifier = Modifier
                .matchParentSize()
                .scale(scale)
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PulsatingIconPreview() {
    PlayShowTheme {
        PulsatingIcon(
            modifier = Modifier.size(100.dp),
            label = "Pulsating Icon",
            imageVector = Icons.Default.SmartDisplay,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
