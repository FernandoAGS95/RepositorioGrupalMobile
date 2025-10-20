package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.app_grupo7.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoCatalogo: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfumería Aura — Inicio") }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // LOGO
            Image(
                painter = painterResource(R.drawable.aura),
                contentDescription = "Logo Perfumería Aura",
                modifier = Modifier
                    .fillMaxWidth(0.52f)
                    .clip(RoundedCornerShape(18.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Bienvenido/a a Perfumería Aura",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "Explora nuestro catálogo y encuentra tu fragancia.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )


            Spacer(Modifier.height(100.dp))

            Button(
                onClick = onGoCatalogo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) { Text("Ver catálogo") }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) { Text("Cerrar sesión") }

            Spacer(Modifier.height(12.dp))
        }
    }
}
