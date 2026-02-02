package ru.sicampus.bootcamp2026.ui.bottom_navigation

import ru.sicampus.bootcamp2026.R

sealed class BottomNavigationItem(val title: String, val iconId: Int, val route: String) {
    object ScreenInbox: BottomNavigationItem("Входящие", R.drawable.ic_inbox, "screen_inbox")
    object ScreenMeetings: BottomNavigationItem("Встречи", R.drawable.ic_meetings, "screen_meetings")
    object ScreenProfile: BottomNavigationItem("Профиль", R.drawable.ic_profile, "screen_profile")
}