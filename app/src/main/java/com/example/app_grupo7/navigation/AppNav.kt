package com.example.app_grupo7.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_grupo7.ui.screens.CatalogoScreen
import com.example.app_grupo7.ui.screens.HomeScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(
                onGoCatalogo = { navController.navigate(NavRoutes.Catalogo.route) }
            )
        }
        composable(NavRoutes.Catalogo.route) {
            CatalogoScreen()
        }
    }
}

