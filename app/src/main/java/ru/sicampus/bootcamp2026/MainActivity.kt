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
import ru.sicampus.bootcamp2026.data.AuthRepository
import ru.sicampus.bootcamp2026.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.data.source.AuthNetworkDataSource
import ru.sicampus.bootcamp2026.domain.auth.GetAuthStatusUseCase
import ru.sicampus.bootcamp2026.ui.nav.AuthViewModel
import ru.sicampus.bootcamp2026.ui.nav.AuthViewModelFactory
import ru.sicampus.bootcamp2026.ui.nav.NavigationGraph
import ru.sicampus.bootcamp2026.ui.theme.AndroidBootcamp2026FrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authRepository = AuthRepository(AuthNetworkDataSource(), AuthLocalDataSource)
        val getAuthStatusUseCase = GetAuthStatusUseCase(authRepository)
        enableEdgeToEdge()
        setContent {
            val authFactory = AuthViewModelFactory(getAuthStatusUseCase)
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)
            AndroidBootcamp2026FrontendTheme{
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationGraph(
                        authViewModel = authViewModel,
                        modifier = Modifier
                                .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}