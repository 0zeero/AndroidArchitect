package com.example.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core.navigation.NavigationManager
import com.example.core.navigation.NavigationCommand

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { 
            // Communication via Core module
            NavigationManager.navigate(NavigationCommand.ToScreen("details")) 
        }) {
            Text("Go to Details (Triggered via Core)")
        }
    }
}
