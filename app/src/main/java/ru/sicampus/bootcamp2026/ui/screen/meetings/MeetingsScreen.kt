package ru.sicampus.bootcamp2026.ui.screen.meetings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.FlowPreview
import ru.sicampus.bootcamp2026.ui.screen.meetings.calendar.Calendar
import java.time.LocalDate

@OptIn(FlowPreview::class)
@Composable

fun MeetingsScreen(
    viewmodel: MeetingViewModel = viewModel<MeetingViewModel>(),
) {
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    Calendar(
        selectedDate = selectedDate,
        onDateSelected = { selectedDate = it }
    )
}