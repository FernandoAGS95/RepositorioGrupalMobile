package com.example.app_grupo7.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.app_grupo7.viewmodel.UsuarioViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    vm: UsuarioViewModel,
    onContinuar: () -> Unit
) {
    val ui by vm.ui.collectAsState()
    val errs by vm.errores.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro") }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = ui.nombre,
                onValueChange = vm::onNombreChange,
                label = { Text("Nombre") },
                isError = errs.nombre != null,
                supportingText = { if (errs.nombre != null) Text(errs.nombre!!) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ui.correo,
                onValueChange = vm::onCorreoChange,
                label = { Text("Correo") },
                isError = errs.correo != null,
                supportingText = { if (errs.correo != null) Text(errs.correo!!) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ui.clave,
                onValueChange = vm::onClaveChange,
                label = { Text("Clave") },
                visualTransformation = PasswordVisualTransformation(),
                isError = errs.clave != null,
                supportingText = { if (errs.clave != null) Text(errs.clave!!) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ui.direccion,
                onValueChange = vm::onDireccionChange,
                label = { Text("Dirección") },
                isError = errs.direccion != null,
                supportingText = { if (errs.direccion != null) Text(errs.direccion!!) },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = ui.aceptaTerminos,
                    onCheckedChange = { vm.onAceptaChange(it) }
                )
                Text("Acepto los términos y condiciones")
            }
            if (errs.aceptaTerminos != null) {
                Text(errs.aceptaTerminos!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    if (vm.validarFormulario()) onContinuar()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}
