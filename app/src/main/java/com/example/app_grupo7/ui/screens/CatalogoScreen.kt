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
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateContentSize
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(vm: PerfumeViewModel = viewModel()) {
    val perfumes by vm.catalogo.collectAsState()

    // Snackbar + scope
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Catálogo") }) },
        snackbarHost = {
            // Host con fade in/out cuando aparece/desaparece el snackbar
            AnimatedVisibility(
                visible = snackbarHostState.currentSnackbarData != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    ) { inner ->
        // Transición entre estado vacío y con datos
        AnimatedContent(
            targetState = perfumes.isEmpty(),
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(12.dp),
            label = "empty-vs-list"
        ) { isEmpty ->
            if (isEmpty) {
                // Estado vacío
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "No hay perfumes disponibles",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Vuelve más tarde o recarga.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                // Lista con datos
                LazyColumn {
                    items(perfumes) { p ->
                        PerfumeCard(
                            p = p,
                            onAdd = {
                                // feedback con snackbar animado
                                scope.launch {
                                    snackbarHostState.showSnackbar("Agregado: ${p.nombre}")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PerfumeCard(
    p: Perfume,
    onAdd: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .animateContentSize() // <-- animación al cambiar altura del contenido
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("${p.marca} - ${p.nombre}", style = MaterialTheme.typography.titleMedium)
            Text("${p.ml} ml • $${p.precio}", style = MaterialTheme.typography.bodyMedium)

            if (expanded) {
                Spacer(Modifier.height(6.dp))
                Text(p.descripcion, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Ocultar" else "Ver más")
                }
                Button(onClick = onAdd) { Text("Agregar") }
            }
        }
    }
}