package ru.sicampus.bootcamp2026.ui.screens.auth.register

sealed interface RegisterIntent {
    data class FieldChanged(val fields: RegisterFields) : RegisterIntent
    data object Submit : RegisterIntent
}