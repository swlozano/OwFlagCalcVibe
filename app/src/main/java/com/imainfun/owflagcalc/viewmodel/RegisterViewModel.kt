package com.imainfun.owflagcalc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imainfun.owflagcalc.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegistrationSuccessful: Boolean = false
)

class RegisterViewModel : ViewModel() {
    
    private val userRepository by lazy { UserRepository() }
    
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    
    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            errorMessage = null
        )
    }
    
    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            errorMessage = null
        )
    }
    
    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errorMessage = null
        )
    }
    
    fun register() {
        val currentState = _uiState.value
        
        // Validate input fields
        if (!validateInputs(currentState)) {
            return
        }
        
        viewModelScope.launch {
            try {
                _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
                
                val result = userRepository.registerUser(
                    email = currentState.email,
                    password = currentState.password,
                    name = currentState.name
                )
                
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRegistrationSuccessful = true
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage ?: "Registration failed"
                    )
                }
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Registration failed: ${e.message}"
                )
            }
        }
    }
    
    private fun validateInputs(state: RegisterUiState): Boolean {
        return when {
            state.name.isBlank() -> {
                _uiState.value = state.copy(errorMessage = "El nombre es requerido")
                false
            }
            state.email.isBlank() -> {
                _uiState.value = state.copy(errorMessage = "El correo electrónico es requerido")
                false
            }
            !isValidEmail(state.email) -> {
                _uiState.value = state.copy(errorMessage = "Formato de correo electrónico inválido")
                false
            }
            state.password.isBlank() -> {
                _uiState.value = state.copy(errorMessage = "La contraseña es requerida")
                false
            }
            state.password.length < 6 -> {
                _uiState.value = state.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
                false
            }
            else -> true
        }
    }
    
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    fun resetRegistrationState() {
        _uiState.value = RegisterUiState()
    }
}