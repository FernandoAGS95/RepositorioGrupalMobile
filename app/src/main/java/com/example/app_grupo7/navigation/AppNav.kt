package com.example.app_grupo7.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_grupo7.data.AppState
import com.example.app_grupo7.ui.screens.LoginScreen
import com.example.app_grupo7.ui.screens.RegistroScreen
import com.example.app_grupo7.ui.screens.CatalogoScreen   // importa catálogo

@Composable
fun AppNav(appState: AppState) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        composable("login")    { LoginScreen(navController, appState) }
        composable("registro") { RegistroScreen(navController, appState) }
        composable("catalogo") { CatalogoScreen() }
    }
}
