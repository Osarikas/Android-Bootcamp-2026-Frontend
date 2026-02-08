package ru.sicampus.bootcamp2026.ui.screen.meetings.calendar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.sicampus.bootcamp2026.ui.screen.meetings.calendar.month.MonthCalendar
import ru.sicampus.bootcamp2026.ui.screen.meetings.calendar.week.WeekCalendar
import java.time.LocalDate

@Composable
fun Calendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var mode  by remember { mutableStateOf(CalendarState.WEEK) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CalendarButton(
            mode = mode,
            onClick = {
                mode =
                    if (mode == CalendarState.WEEK)
                        CalendarState.MONTH
                    else
                        CalendarState.WEEK
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = mode,
            transitionSpec = {
                (expandVertically() + fadeIn())
                    .togetherWith(
                        shrinkVertically() + fadeOut()
                    )
                    .using(
                        SizeTransform(clip = false)
                    )
            }
        ) { targetMode ->
            when (targetMode) {
                CalendarState.WEEK -> {
                    WeekCalendar(
                        selectedDate = selectedDate,
                        onDateSelected = onDateSelected
                    )
                }

                CalendarState.MONTH -> {
                    MonthCalendar(
                        selectedDate = selectedDate,
                        onDateSelected = onDateSelected
                    )
                }
            }
        }
    }
}