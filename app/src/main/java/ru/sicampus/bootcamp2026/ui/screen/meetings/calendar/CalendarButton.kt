package ru.sicampus.bootcamp2026.ui.screen.meetings.calendar

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CalendarButton(
    mode: CalendarState,
    onClick: () -> Unit
) {
    Text(
        text =
            if (mode == CalendarState.WEEK)
                "Open month"
            else
                "Close month",
        modifier = Modifier
            .clickable {
                onClick()
            }
    )
}