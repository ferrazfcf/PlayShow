package com.ferraz.playshow.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.delay

@Composable
fun PlayShowRoot() {
    val navController = rememberNavController()

    var bottomBarDestination by remember { mutableStateOf<Routes>(Splash) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            PlayShowBottomBar(
                route = bottomBarDestination,
                onAction = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        PlayShowContent(navController, innerPadding) { route ->
            bottomBarDestination = route
        }
    }
}

@Composable
private fun PlayShowContent(
    navController: NavHostController,
    innerPadding: PaddingValues,
    onNavigate: (Routes) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        composable<Splash> {
            onNavigate(Splash)
            LaunchedEffect(Unit) {
                delay(5000)
                navController.navigate(Home) {
                    popUpTo(Splash) {
                        inclusive = true
                    }
                }
            }
            Text(text = "Splash")
        }
        composable<Home> {
            onNavigate(Home)
            Text(text = "Home")
        }
        composable<MyList> {
            onNavigate(MyList)
            Text(text = "My List")
        }
        composable<MovieDetails> { backStackEntry ->
            val movieDetails: MovieDetails = backStackEntry.toRoute()
            onNavigate(MovieDetails(movieDetails.id))
            Text(text = "Movie Details: ${movieDetails.id}")
        }
    }
}
