package com.example.androidarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.NavigationManager
import com.example.core.navigation.NavigationCommand
import com.example.features.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            // Listen to navigation commands from Core
            LaunchedEffect(Unit) {
                NavigationManager.commands.collect { command ->
                    when (command) {
                        is NavigationCommand.ToScreen -> navController.navigate(command.route)
                        is NavigationCommand.Back -> navController.popBackStack()
                    }
                }
            }

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen()
                }
                composable("details") {
                    // Placeholder for another feature
                   Text("Details Screen")
                }
            }
        }
    }
}
