package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_grupo7.data.AppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, appState: AppState){
    var usuario by remember { mutableStateOf("") }
    var password by remember {  mutableStateOf("") }
    var error by remember {  mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Login") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            if (error.isNotEmpty()){
                Text(error, color= MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }
            Button(
                onClick = {
                    if (usuario.isBlank() || password.isBlank()){
                        error = "Debe Ingresar usuario y contraseña"
                    }else if (appState.login(usuario, password)){
                        error = ""
                        navController.navigate("catalogo") {
                            popUpTo("login") { inclusive = true }  //  para que no vuelva al login con Back
                        }
                    }else{
                        error = "Usuario y/o Contraseña incorrectos"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Inicio de Sesión") }

            TextButton(onClick = { navController.navigate("registro")}) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
