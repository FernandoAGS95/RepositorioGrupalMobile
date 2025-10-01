package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onGoCatalogo: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfumería") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Bienvenido a AppPerfumes")
            Button(onClick = onGoCatalogo) {
                Text("Explorar perfumes")
            }
        }
    }
}
