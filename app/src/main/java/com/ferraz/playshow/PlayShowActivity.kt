package com.ferraz.playshow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ferraz.playshow.presentation.navigation.PlayShowRoot
import com.ferraz.playshow.presentation.theme.PlayShowTheme

class PlayShowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayShowTheme {
                PlayShowRoot()
            }
        }
    }
}
