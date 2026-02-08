package ru.sicampus.bootcamp2026.ui.screen.meetings.calendar.month

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.sicampus.bootcamp2026.ui.screen.meetings.calendar.DayItem
import ru.sicampus.bootcamp2026.ui.screen.meetings.calendar.WeekHeader
import java.time.LocalDate

@Composable
fun MonthCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = remember {
        LocalDate.now()
    }
    val pagerState = rememberPagerState(
        initialPage = 5000,
        pageCount = { 10000 }
    )
    val currentMonth = remember { selectedDate.withDayOfMonth(1) }

    Column {
        WeekHeader(selectedDate)
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val month = currentMonth.plusMonths(
                (page - 5000).toLong()
            )
            val days = generateMonthDays(month)

            repeat(days.size / 7) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(7) { column ->
                        val index =
                            row * 7 + column
                        val date =
                            days[index]
                        if (date != null) {
                            DayItem(
                                date = date,
                                isSelected =
                                    date == selectedDate,
                                isToday =
                                    date == today,
                                onClick = {
                                    onDateSelected(date)
                                }
                            )
                        } else {
                            EmptyDay()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyDay() {
    Box(
        modifier = Modifier
            .size(36.dp)
    )
}
