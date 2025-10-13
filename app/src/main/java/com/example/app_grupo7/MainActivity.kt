package com.example.app_grupo7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.app_grupo7.data.AppState
import com.example.app_grupo7.data.DataStoreManager
import com.example.app_grupo7.navigation.AppNav


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = DataStoreManager(applicationContext)
        val appState = AppState(dataStore)
        appState.cargarDatos() // carga inicial desde DataStore

        setContent {
            MaterialTheme {
                AppNav(appState)
            }
        }
    }
}
