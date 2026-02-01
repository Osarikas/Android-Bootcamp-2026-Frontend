package ru.sicampus.bootcamp2026.ui.main.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.sicampus.bootcamp2026.ui.main.screens.Inbox
import ru.sicampus.bootcamp2026.ui.main.screens.Meetings
import ru.sicampus.bootcamp2026.ui.main.screens.Profile

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = "screen_meetings") {
        composable("screen_inbox") {
            Inbox()
        }
        composable("screen_meetings") {
            Meetings()
        }
        composable("screen_profile") {
            Profile()
        }
    }
}