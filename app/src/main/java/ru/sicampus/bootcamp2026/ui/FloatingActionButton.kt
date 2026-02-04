package ru.sicampus.bootcamp2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.ui.nav.AppRoute
import ru.sicampus.bootcamp2026.ui.theme.Black
import ru.sicampus.bootcamp2026.ui.theme.White

@Composable
fun FloatingActionButton (
    navController: NavController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    // val currentRoute = backStackEntry?.destination?.route
    val isProfileRoute = backStackEntry?.destination?.hasRoute(AppRoute.ProfileRoute::class) ?: false

    val iconId: Int
    val iconContentDescription: String
    val action: () -> Unit

    if (isProfileRoute) {
        iconId = R.drawable.ic_edit
        iconContentDescription = "Изменить"
        action = {}
    }
    else {
        iconId = R.drawable.ic_add
        iconContentDescription = "Добавить"
        action = {}
    }

    Box (
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Black)
            .clickable(
                onClick = action
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = iconContentDescription,
            modifier = Modifier.size(40.dp),
            tint = White
        )
    }
}