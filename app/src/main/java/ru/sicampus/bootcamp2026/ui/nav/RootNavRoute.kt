package ru.sicampus.bootcamp2026.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.domain.auth.AuthState
import ru.sicampus.bootcamp2026.ui.MainScreen
import ru.sicampus.bootcamp2026.ui.screen.auth.login.LoginScreen
import ru.sicampus.bootcamp2026.ui.screen.auth.register.RegisterScreen

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
                NavHost(
                    navController = rootNavController,
                    startDestination = AppRoute.LoginRoute
                ) {
                    composable<AppRoute.LoginRoute> {
                        LoginScreen(
                            onNavigateToRegister = { rootNavController.navigate(AppRoute.RegisterRoute) },
                            navController = rootNavController
                        )
                    }
                    composable<AppRoute.RegisterRoute> {
                        RegisterScreen(navController = rootNavController)
                    }
                }
            }
            is AuthState.Authorized -> {
                MainScreen()
            }
        }
    }
}