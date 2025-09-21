package com.example.app_grupo7.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_grupo7.cart.CarritoViewModel
import com.example.app_grupo7.data.AppState
import com.example.app_grupo7.ui.screens.*
import com.example.app_grupo7.viewmodel.AuthVMFactory
import com.example.app_grupo7.viewmodel.AuthViewModel

@Composable
fun AppNav(
    appState: AppState,
    carritoVm: CarritoViewModel
) {
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

        composable("home") {
            HomeScreen(
                onGoCatalogo = { navController.navigate("catalogo") },
                onLogout = {
                    appState.logout()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onGoCrud = { navController.navigate("perfume_crud") } // acceso al CRUD
            )
        }

        composable("catalogo") {
            CatalogoScreen(
                carritoVm = carritoVm,
                onGoCarrito = { navController.navigate("carrito") }
            )
        }

        composable("carrito") {
            CarritoScreen(carritoVm = carritoVm)
        }

        composable("perfume_crud") {
            PerfumeCrudScreen()
        }
    }
}
