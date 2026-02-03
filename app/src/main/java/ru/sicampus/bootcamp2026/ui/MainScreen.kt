package ru.sicampus.bootcamp2026.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.domain.auth.AuthState
import ru.sicampus.bootcamp2026.ui.nav.AuthViewModel
import ru.sicampus.bootcamp2026.ui.nav.bottom.BottomNavigation
import ru.sicampus.bootcamp2026.ui.nav.bottom.NavGraph
import ru.sicampus.bootcamp2026.ui.screen.login.LoginScreen
import ru.sicampus.bootcamp2026.ui.screen.login.LoginViewModel

@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    rootNavController: NavController
) {
    val authState by authViewModel.authState.collectAsState()
    val bottomNavController = rememberNavController()

    when (authState) {
        is AuthState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) { CircularProgressIndicator() }
        }

        is AuthState.Authorized -> {
            androidx.compose.material3.Scaffold(
                bottomBar = {
                    BottomNavigation(bottomNavController)
                }
            ) { innerPadding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ) {
                    NavGraph(bottomNavController)
                }
            }
        }

        is AuthState.Unauthorized -> {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory()
            )
            LoginScreen(loginViewModel, rootNavController)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel() as T
    }
}