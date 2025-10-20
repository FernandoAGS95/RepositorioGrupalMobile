package com.example.app_grupo7.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_grupo7.data.AppState
import com.example.app_grupo7.ui.screens.*
import com.example.app_grupo7.viewmodel.AuthVMFactory
import com.example.app_grupo7.viewmodel.AuthViewModel

@Composable
fun AppNav(appState: AppState) {
    val navController = rememberNavController()
    val authVm: AuthViewModel = viewModel(factory = AuthVMFactory(appState))

    NavHost(navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                navController = navController,
                vm = authVm
            )
        }

        composable("registro") {
            RegistroScreen(
                navController = navController,
                vm = authVm
            )
        }

        // pantalla principal
        composable("home") {
            HomeScreen(
                onGoCatalogo = { navController.navigate("catalogo") },
                onLogout = {
                    appState.logout()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("catalogo") {
            CatalogoScreen(

            )
        }
    }
}