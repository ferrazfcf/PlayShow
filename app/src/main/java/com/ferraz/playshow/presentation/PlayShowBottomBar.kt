package com.ferraz.playshow.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.toRoute

@Composable
fun PlayShowBottomBar(navController: NavController) {
    when(val route = navController.currentBackStackEntry?.toRoute<Routes>()) {
        Home, MyList -> BottomBar(navController, route)
        else -> Unit
    }
}

@Composable
fun BottomBar(navController: NavController, route: Routes) {
    TODO("Not yet implemented")
}
