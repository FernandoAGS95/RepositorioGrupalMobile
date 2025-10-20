package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_grupo7.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController, vm: AuthViewModel) {
    val ui     by vm.ui.collectAsState()
    val errors by vm.errors.collectAsState()

    var showPwd1 by remember { mutableStateOf(false) }
    var showPwd2 by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Usuario") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = ui.email,
                onValueChange = vm::onEmailChange,
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                isError = errors.email != null,
                supportingText = { errors.email?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = ui.password,
                onValueChange = vm::onPasswordChange,
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { showPwd1 = !showPwd1 }) {
                        Icon(
                            imageVector = if (showPwd1) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPwd1) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                isError = errors.password != null,
                supportingText = { errors.password?.let { Text(it) } },
                visualTransformation = if (showPwd1) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = ui.confirmPassword,
                onValueChange = vm::onConfirmChange,
                label = { Text("Confirmar contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { showPwd2 = !showPwd2 }) {
                        Icon(
                            imageVector = if (showPwd2) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPwd2) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                isError = errors.confirmPassword != null,
                supportingText = { errors.confirmPassword?.let { Text(it) } },
                visualTransformation = if (showPwd2) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            errors.general?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (vm.register()) {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Registrarse") }
        }
    }
}
