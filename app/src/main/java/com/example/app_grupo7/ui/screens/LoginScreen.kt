package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app_grupo7.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: (email: String, password: String) -> Unit,
    onGoRegistro: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Bienvenido", textAlign = TextAlign.Center) }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            // Logo / imagen centrada
            Image(
                painter = painterResource(id = R.drawable.aura),
                contentDescription = "Aurora",
                modifier = Modifier
                    .size(400.dp)

                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { onLogin(email.trim(), pass) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ingresar") }

            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = onGoRegistro,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Crear cuenta") }
        }
    }
}
