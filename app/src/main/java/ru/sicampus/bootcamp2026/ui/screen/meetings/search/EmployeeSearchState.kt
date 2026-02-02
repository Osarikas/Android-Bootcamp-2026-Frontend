package ru.sicampus.bootcamp2026.ui.screen.meetings.search

import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity

sealed interface EmployeeSearchState {
    data class Error(
        val reason: String
    ): EmployeeSearchState
    data object Loading: EmployeeSearchState
    data class Content(
        val users: List<EmployeeEntity>,
        val inputError: String? = null
    ) : EmployeeSearchState
}