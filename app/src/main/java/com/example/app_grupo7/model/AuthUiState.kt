package com.example.app_grupo7.model


data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",   // vacío en login, usado en registro
    val isLoading: Boolean = false
)

data class AuthErrors(
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val general: String? = null         // para errores globales (credenciales inválidas, etc.)
)
