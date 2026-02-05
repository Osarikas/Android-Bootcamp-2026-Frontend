package ru.sicampus.bootcamp2026.ui.screens.profile

import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity

sealed interface ProfileIntent {
    data object ToggleEditMode : ProfileIntent
    data class UpdateField(val updatedEmployee: EmployeeEntity) : ProfileIntent
    data object SaveProfile : ProfileIntent
}