package com.imainfun.owflagcalc.repository

import com.imainfun.owflagcalc.config.SupabaseConfig
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

data class RegisterResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null,
    val userId: String? = null
)

class UserRepository {
    
    private val supabaseClient = SupabaseConfig.client
    
    suspend fun registerUser(
        email: String,
        password: String,
        name: String
    ): RegisterResult {
        return try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("name", name)
                }
            }
            
            val currentUser = supabaseClient.auth.currentUserOrNull()
            
            RegisterResult(
                isSuccess = true,
                userId = currentUser?.id
            )
        } catch (e: Exception) {
            RegisterResult(
                isSuccess = false,
                errorMessage = e.message ?: "Registration failed"
            )
        }
    }
    
    suspend fun loginUser(email: String, password: String): RegisterResult {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            
            val currentUser = supabaseClient.auth.currentUserOrNull()
            
            RegisterResult(
                isSuccess = true,
                userId = currentUser?.id
            )
        } catch (e: Exception) {
            RegisterResult(
                isSuccess = false,
                errorMessage = e.message ?: "Login failed"
            )
        }
    }
    
    suspend fun getCurrentUser() = supabaseClient.auth.currentUserOrNull()
    
    suspend fun signOut() {
        try {
            supabaseClient.auth.signOut()
        } catch (e: Exception) {
            // Handle sign out error if needed
        }
    }
}