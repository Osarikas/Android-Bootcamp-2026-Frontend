package ru.sicampus.bootcamp2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sicampus.bootcamp2026.ui.nav.NavigationGraph
import ru.sicampus.bootcamp2026.ui.theme.AndroidBootcamp2026FrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBootcamp2026FrontendTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(
                        authViewModel = viewModel(),
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
            }
        }
    }
}