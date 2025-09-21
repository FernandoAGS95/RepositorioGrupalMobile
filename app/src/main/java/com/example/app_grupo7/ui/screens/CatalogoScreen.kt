package com.example.app_grupo7.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_grupo7.cart.CarritoViewModel      // ← import
import com.example.app_grupo7.model.Perfume
import com.example.app_grupo7.ui.Dimens
import com.example.app_grupo7.viewmodel.PerfumeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    vm: PerfumeViewModel = viewModel(),
    carritoVm: CarritoViewModel,                             // ← NUEVO parámetro
    onGoPerfil: (() -> Unit)? = null,
    onGoCarrito: (() -> Unit)? = null
) {
    val perfumes by vm.catalogo.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de perfumes") },
                actions = {
                    onGoCarrito?.let { TextButton(onClick = it) { Text("Ver carrito") } }
                }
            )
        },
        snackbarHost = {
            AnimatedVisibility(
                visible = snackbarHostState.currentSnackbarData != null,
                enter = fadeIn(), exit = fadeOut()
            ) { SnackbarHost(hostState = snackbarHostState) }
        }
    ) { inner ->

        val contentModifier = Modifier
            .padding(inner)
            .fillMaxSize()
            .padding(Dimens.screenPadding)

        AnimatedContent(
            targetState = perfumes.isEmpty(),
            modifier = contentModifier,
            label = "empty-vs-grid"
        ) { isEmpty ->
            if (isEmpty) {
                EmptyState(
                    icon = { Icon(Icons.Outlined.Inventory2, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                    title = "No hay perfumes disponibles",
                    subtitle = "Vuelve más tarde o recarga el catálogo."
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = Dimens.minGridCell),
                    verticalArrangement = Arrangement.spacedBy(Dimens.cardSpacing),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.cardSpacing)
                ) {
                    items(perfumes) { p ->
                        PerfumeCard(
                            p = p,
                            onAdd = {
                                carritoVm.addFromPerfume(         // ← agregar al carrito
                                    id = p.id,
                                    nombre = p.nombre,
                                    precio = p.precio,
                                    imageRes = p.imageRes
                                )
                                scope.launch { snackbarHostState.showSnackbar("Agregado: ${p.nombre}") }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PerfumeCard(p: Perfume, onAdd: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(Dimens.cardInner)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                p.imageRes?.let { resId ->
                    Image(
                        painter = painterResource(resId),
                        contentDescription = "${p.marca} ${p.nombre}",
                        modifier = Modifier.size(84.dp).clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.width(12.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("${p.marca} — ${p.nombre}", style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(4.dp))
                    Text("${p.ml} ml • $${p.precio}", style = MaterialTheme.typography.bodyMedium)
                }
            }

            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(p.descripcion, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))
            TextButton(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
                Text(if (expanded) "Ocultar" else "Ver más")
            }
            Spacer(Modifier.height(6.dp))
            Button(onClick = onAdd, modifier = Modifier.fillMaxWidth()) { Text("Agregar") }
        }
    }
}

@Composable
private fun EmptyState(icon: @Composable () -> Unit, title: String, subtitle: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Spacer(Modifier.height(12.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
