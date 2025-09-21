package com.example.app_grupo7.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_grupo7.cart.CarritoViewModel
import com.example.app_grupo7.data.AppState
import com.example.app_grupo7.ui.screens.*

@Composable
fun AppNav(
    appState: AppState,
    carritoVm: CarritoViewModel
) {
    val navController = rememberNavController()

    // Email en memoria local (sobrevive rotaciones con saveable)
    var currentEmail by rememberSaveable { mutableStateOf<String?>(null) }
    val isAdmin = currentEmail.equals("admin@aurora.cl", ignoreCase = true)

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onLogin = { email, _ ->
                    currentEmail = email.trim()
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onGoRegistro = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            // Registro mínimo: si quieres, solo vuelve a login.
            RegistroScreen(
                onRegister = { email, _ ->
                    // Si quieres que registre y entre directo:
                    currentEmail = email.trim()
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("home") {
            HomeScreen(
                showCreateButton = isAdmin,
                onGoCatalogo = { navController.navigate("catalogo") },
                onLogout = {
                    currentEmail = null
                    appState.logout() // si ya lo usabas para limpiar DataStore, mantenlo; si no, quítalo
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
