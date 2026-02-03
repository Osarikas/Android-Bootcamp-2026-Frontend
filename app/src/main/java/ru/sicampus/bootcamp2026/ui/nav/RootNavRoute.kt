package ru.sicampus.bootcamp2026.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.domain.auth.AuthState
import ru.sicampus.bootcamp2026.ui.LoginViewModelFactory
import ru.sicampus.bootcamp2026.ui.MainScreen
import ru.sicampus.bootcamp2026.ui.screen.login.LoginScreen
import ru.sicampus.bootcamp2026.ui.screen.login.LoginViewModel

@Composable
fun NavigationGraph(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val authState by authViewModel.authState.collectAsState()
    val rootNavController = rememberNavController()


    Box(modifier = modifier) {
        when (authState) {
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthState.Unauthorized -> {
                val loginViewModel: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory()
                )
                LoginScreen(viewModel = loginViewModel, navController = rootNavController)
            }
            is AuthState.Authorized -> {
                MainScreen(
                    rootNavController = rootNavController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}