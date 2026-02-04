package ru.sicampus.bootcamp2026.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.ui.nav.AuthViewModel
import ru.sicampus.bootcamp2026.ui.nav.bottom.BottomNavigation
import ru.sicampus.bootcamp2026.ui.nav.bottom.NavGraph

@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    rootNavController: NavController
) {
    val bottomNavController = rememberNavController()

    androidx.compose.material3.Scaffold(
        bottomBar = { BottomNavigation(bottomNavController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(bottomNavController)
        }
    }
}

