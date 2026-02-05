package ru.sicampus.bootcamp2026.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.ui.nav.bottom.BottomNavigation
import ru.sicampus.bootcamp2026.ui.nav.bottom.NavGraph

@Composable
fun MainScreen() {
    val bottomNavController = rememberNavController()
    val activity = LocalActivity.current as ComponentActivity
    val fabVm = viewModel<FABViewModel>(activity)
    LaunchedEffect(bottomNavController) {
        bottomNavController.currentBackStackEntryFlow.collect {
            fabVm.setConfig(null)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        NavGraph(bottomNavController)

        Box(
            modifier = Modifier
                .padding(bottom = 128.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        ) {
            FloatingActionButton ()
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(1f)
        ) {
            BottomNavigation(bottomNavController)
        }
    }
}

