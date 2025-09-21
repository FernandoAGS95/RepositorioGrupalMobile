package com.example.app_grupo7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_grupo7.data.AppState
import com.example.app_grupo7.model.AuthUiState
import com.example.app_grupo7.model.AuthErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(private val appState: AppState) : ViewModel() {

    // estado observable por la UI
    private val _ui = MutableStateFlow(AuthUiState())
    val ui: StateFlow<AuthUiState> = _ui

    private val _errors = MutableStateFlow(AuthErrors())
    val errors: StateFlow<AuthErrors> = _errors

    // --------- setters  ----------
    fun onEmailChange(v: String)       { clearFieldError("email"); _ui.value = _ui.value.copy(email = v) }
    fun onPasswordChange(v: String)    { clearFieldError("password"); _ui.value = _ui.value.copy(password = v) }
    fun onConfirmChange(v: String)     { clearFieldError("confirmPassword"); _ui.value = _ui.value.copy(confirmPassword = v) }

    private fun clearFieldError(field: String) {
        val e = _errors.value
        _errors.value = when (field) {
            "email" -> e.copy(email = null, general = null)
            "password" -> e.copy(password = null, general = null)
            "confirmPassword" -> e.copy(confirmPassword = null, general = null)
            else -> e
        }
    }

    // --------- validaciones ----------
    private fun validateLogin(): Boolean {
        val u = _ui.value
        val errs = AuthErrors(
            email = when {
                u.email.isBlank()      -> "El email es obligatorio"
                !u.email.contains("@") -> "Email no válido"
                else -> null
            },
            password = when {
                u.password.isBlank()   -> "La contraseña es obligatoria"
                u.password.length < 4  -> "Mínimo 4 caracteres"
                else -> null
            }
        )
        _errors.value = errs
        return listOf(errs.email, errs.password).all { it == null }
    }

    private fun validateRegister(): Boolean {
        val u = _ui.value
        val errs = AuthErrors(
            email = when {
                u.email.isBlank()      -> "El email es obligatorio"
                !u.email.contains("@") -> "Email no válido"
                else -> null
            },
            password = when {
                u.password.isBlank()   -> "La contraseña es obligatoria"
                u.password.length < 4  -> "Mínimo 4 caracteres"
                else -> null
            },
            confirmPassword = when {
                u.confirmPassword.isBlank() -> "Confirma tu contraseña"
                u.confirmPassword != u.password -> "Las contraseñas no coinciden"
                else -> null
            }
        )
        _errors.value = errs
        return listOf(errs.email, errs.password, errs.confirmPassword).all { it == null }
    }

    // --------- acciones (lógica) ----------
    fun login(): Boolean {
        if (!validateLogin()) return false
        val ok = appState.login(_ui.value.email, _ui.value.password)
        if (!ok) {
            _errors.value = _errors.value.copy(general = "Usuario y/o contraseña incorrectos")
        } else {
            // limpiar errores globales
            _errors.value = _errors.value.copy(general = null)
        }
        return ok
    }

    fun register(): Boolean {
        if (!validateRegister()) return false
        val ok = appState.registrarUsuario(_ui.value.email, _ui.value.password)
        if (!ok) {
            _errors.value = _errors.value.copy(general = "El usuario ya existe")
        } else {
            _errors.value = AuthErrors()
            // opcional: limpiar campos después de registro
            _ui.value = AuthUiState()
        }
        return ok
    }
}

// Factory para inyectar AppState
class AuthVMFactory(private val appState: AppState) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(appState) as T
    }
}


