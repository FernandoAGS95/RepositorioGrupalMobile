package com.example.app_grupo7.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_grupo7.ui.screens.CatalogoScreen
import com.example.app_grupo7.ui.screens.HomeScreen
import com.example.app_grupo7.ui.screens.RegistroScreen
import com.example.app_grupo7.ui.screens.ResumenScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_grupo7.viewmodel.UsuarioViewModel

@Composable
fun AppNav() {
    val navController = rememberNavController()
    // ViewModel compartido para el flujo de registro
    val usuarioVm: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(
                onGoCatalogo = { navController.navigate(NavRoutes.Catalogo.route) },
                onGoRegistro = { navController.navigate(NavRoutes.Registro.route) }
            )
        }
        composable(NavRoutes.Catalogo.route) {
            CatalogoScreen()
        }
        composable(NavRoutes.Registro.route) {
            RegistroScreen(
                vm = usuarioVm,
                onContinuar = { navController.navigate(NavRoutes.Resumen.route) }
            )
        }
        composable(NavRoutes.Resumen.route) {
            ResumenScreen(vm = usuarioVm)
        }
    }
}

