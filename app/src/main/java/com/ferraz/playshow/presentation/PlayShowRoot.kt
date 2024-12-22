package com.ferraz.playshow.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun PlayShowRoot() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { PlayShowBottomBar(navController) }
    ) { innerPadding ->
        PlayShowContent(navController, innerPadding)
    }
}

@Composable
private fun PlayShowContent(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        composable<Splash> {
            TODO()
        }
        composable<Home> {
            TODO()
        }
        composable<MyList> {
            TODO()
        }
        composable<MovieDetails> { backStackEntry ->
            val movieDetails: MovieDetails = backStackEntry.toRoute()
            TODO()
        }
    }
}
