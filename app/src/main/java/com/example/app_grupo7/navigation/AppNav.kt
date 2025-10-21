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
import com.example.app_grupo7.cart.CarritoViewModel

@Composable
fun AppNav(
    appState: AppState,
    carritoVm: CarritoViewModel
) {
    val navController = rememberNavController()

    // ViewModel compartido para login/registro
    val authVm: AuthViewModel = viewModel(factory = AuthVMFactory(appState))

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController = navController, vm = authVm)
        }

        composable("registro") {
            RegistroScreen(navController = navController, vm = authVm)
        }

        composable("home") {
            // Admin si el usuario logeado tiene este email
            val isAdmin = appState.usuarioActual
                ?.email
                ?.equals("admin@aura.cl", ignoreCase = true) == true

            HomeScreen(
                showCreateButton = isAdmin,
                onGoCatalogo = { navController.navigate("catalogo") },
                onLogout = {
                    appState.logout()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onGoCrud = { navController.navigate("perfume_crud") }
            )
        }

        composable("catalogo") {
            CatalogoScreen(
                onGoCarrito = { navController.navigate("carrito") },
                onAddToCart = { id, nombre, precio, imageRes, imageUri ->
                    carritoVm.addOrIncrement(
                        perfumeId = id,
                        nombre = nombre,
                        precio = precio,
                        imageRes = imageRes,
                        imageUri = imageUri
                    )
                }
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
