package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    showCreateButton: Boolean,
    onGoCatalogo: () -> Unit,
    onLogout: () -> Unit,
    onGoCrud: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Inicio") }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Bienvenid@", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onGoCatalogo,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ver catálogo") }

            Spacer(Modifier.height(12.dp))

            if (showCreateButton) {
                Button(
                    onClick = onGoCrud,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Crear producto") }
                Spacer(Modifier.height(12.dp))
            }

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Cerrar sesión") }
        }
    }
}
