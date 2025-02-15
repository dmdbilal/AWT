package com.dmdbilal.agroweathertip.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SettingsScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsScreen()
        }
    }
}

@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("User") }
    var selectedTheme by remember { mutableStateOf("Light") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Notifications Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Enable Notifications", fontSize = 18.sp)
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Change Username
            Text(text = "Username", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.small)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Theme Selector
            Text(text = "Select Theme", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                listOf("Light", "Dark", "System").forEach { theme ->
                    RadioButton(
                        selected = selectedTheme == theme,
                        onClick = { selectedTheme = theme }
                    )
                    Text(
                        text = theme,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // About Section
            Button(
                onClick = { /* Navigate to About Screen */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "About")
            }
        }
    }
}
