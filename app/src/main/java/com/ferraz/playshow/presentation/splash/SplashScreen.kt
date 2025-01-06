package com.ferraz.playshow.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferraz.playshow.presentation.components.PulsatingIcon
import com.ferraz.playshow.presentation.navigation.Home
import com.ferraz.playshow.presentation.navigation.Routes
import com.ferraz.playshow.presentation.theme.PlayShowTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateTo: (Routes) -> Unit) {
    LaunchedEffect(Unit) {
        delay(ANIMATION_DURATION)
        navigateTo(Home)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PulsatingIcon(
            modifier = Modifier.size(120.dp).align(Alignment.Center),
            label = "Splash Screen",
            imageVector = Icons.Default.SmartDisplay,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    PlayShowTheme {
        SplashScreen(navigateTo = {})
    }
}

private const val ANIMATION_DURATION: Long = 5_000
