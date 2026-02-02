package ru.sicampus.bootcamp2026.domain

import ru.sicampus.bootcamp2026.data.EmployeeRepository
import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity

class SearchEmployeesUseCase(
    private val employeeRepository: EmployeeRepository
){
    private val nameRegex = Regex("^[a-zA-Zа-яА-ЯёЁ\\s]*$")
    suspend operator fun invoke(query: String?): Result<List<EmployeeEntity>>{
        if (!query.isNullOrBlank() && !nameRegex.matches(query)) {
            return Result.failure(IllegalArgumentException("Строка содержит недопустимые символы"))
        }
        return employeeRepository.searchEmployees(query)
    }
}