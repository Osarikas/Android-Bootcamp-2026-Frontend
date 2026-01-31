package ru.sicampus.bootcamp2026.ui.screen.list

import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity

sealed interface ListState {
    data class Error(
        val reason: String
    ): ListState
    data object Loading: ListState
    data class Content(
        val users: List<EmployeeEntity>
    ) : ListState
}