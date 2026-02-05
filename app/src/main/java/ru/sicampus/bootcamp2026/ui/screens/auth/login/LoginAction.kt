package ru.sicampus.bootcamp2026.ui.screens.auth.login

import ru.sicampus.bootcamp2026.ui.nav.AppRoute

sealed interface LoginAction {
    data class OpenScreen(val route: AppRoute) : LoginAction
}