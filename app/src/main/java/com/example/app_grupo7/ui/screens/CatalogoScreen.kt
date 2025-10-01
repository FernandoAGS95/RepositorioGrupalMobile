package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_grupo7.viewmodel.PerfumeViewModel
import com.example.app_grupo7.model.Perfume
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(vm: PerfumeViewModel = viewModel()) {
    val perfumes by vm.catalogo.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Catálogo") }) }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(perfumes) { p -> PerfumeCard(p) }
        }
    }
}

@Composable
private fun PerfumeCard(p: Perfume) {
    Card {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("${p.marca} — ${p.nombre}", style = MaterialTheme.typography.titleMedium)
            Text("${p.ml} ml  •  \$${p.precio}")
            Text(p.descripcion, style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = { /* luego: agregar al carrito */ },
                modifier = Modifier.padding(top = 8.dp)
            ) { Text("Agregar") }
        }
    }
}
