package bottom_nav

import Screens.InboxScreen
import Screens.MeetingsScreen
import Screens.ProfileScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    var currentRoute by remember { mutableStateOf(BottomNavItem.Meetings.route) }

    val bottomNavState = BottomNavState(
        currentRoute = currentRoute,
        onNavigate = { route ->
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true

                currentRoute = route
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Meetings.route
    ) {
        composable(BottomNavItem.Inbox.route) {
            InboxScreen()
            currentRoute = BottomNavItem.Inbox.route
        }
        composable(BottomNavItem.Meetings.route) {
            MeetingsScreen()
            currentRoute = BottomNavItem.Meetings.route
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
            currentRoute = BottomNavItem.Profile.route
        }
    }
}