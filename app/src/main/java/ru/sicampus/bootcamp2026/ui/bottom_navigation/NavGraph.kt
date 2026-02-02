package ru.sicampus.bootcamp2026.ui.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.sicampus.bootcamp2026.ui.screen.inbox.Inbox
import ru.sicampus.bootcamp2026.ui.screen.meetings.Meetings
import ru.sicampus.bootcamp2026.ui.screen.profile.Profile

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