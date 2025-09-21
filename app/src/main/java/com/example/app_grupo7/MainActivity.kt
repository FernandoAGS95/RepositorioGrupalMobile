package com.example.app_grupo7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_grupo7.cart.CarritoViewModel
import com.example.app_grupo7.cart.CartStore            // ← usa este si tu VM recibe CartStore
// import com.example.app_grupo7.cart.store.CartStoreSqlite // ← usa este si tu VM recibe CartStoreSqlite
import com.example.app_grupo7.data.AppState
import com.example.app_grupo7.data.DataStoreManager
import com.example.app_grupo7.navigation.AppNav
import com.example.app_grupo7.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = DataStoreManager(applicationContext)
        val appState = AppState(dataStore)
        appState.cargarDatos()

        setContent {
            AppTheme {
                // CREA EL VM DEL CARRITO CON FACTORY (evita crash por constructor sin args)
                val carritoVm = viewModel<CarritoViewModel>(factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return CarritoViewModel(
                            CartStore(applicationContext)
                            // CartStoreSqlite(applicationContext)   // ← alterna si tu VM espera esto
                        ) as T
                    }
                })

                AppNav(appState = appState, carritoVm = carritoVm)
            }
        }
    }
}
