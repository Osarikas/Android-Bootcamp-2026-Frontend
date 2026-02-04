package ru.sicampus.bootcamp2026.data.dto

import kotlinx.serialization.Serializable

@Serializable
class PagingEmployeeListDTO(
    @Serializable
    val content: List<EmployeeDTO>?
)