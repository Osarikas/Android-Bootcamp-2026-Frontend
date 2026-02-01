package ru.sicampus.bootcamp2026.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.ui.main.bottom_navigation.BottomNavigation
import ru.sicampus.bootcamp2026.ui.main.bottom_navigation.NavGraph
import ru.sicampus.bootcamp2026.ui.screen.list.ListScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        NavGraph(navController)
        ListScreen() //Временно, чтобы отобразить список

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigation(navController)
        }
    }
}