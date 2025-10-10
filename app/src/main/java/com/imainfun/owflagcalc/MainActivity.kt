package com.imainfun.owflagcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.imainfun.owflagcalc.ui.RegisterScreen
import com.imainfun.owflagcalc.ui.theme.OwFlagCalcTripClaudeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OwFlagCalcTripClaudeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterScreen(
                        modifier = Modifier.padding(innerPadding),
                        onRegisterSuccess = {
                            // Handle successful registration
                            println("Registration successful!")
                        },
                        onLoginClick = {
                            // Handle navigation to login screen
                            println("Navigate to login")
                        }
                    )
                }
            }
        }
    }
}