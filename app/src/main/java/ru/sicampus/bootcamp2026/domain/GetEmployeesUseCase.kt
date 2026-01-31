package ru.sicampus.bootcamp2026.domain

import ru.sicampus.bootcamp2026.data.EmployeeRepository
import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity

class GetEmployeesUseCase(
    private val employeeRepository: EmployeeRepository
){
    suspend operator fun invoke(): Result<List<EmployeeEntity>>{
        return employeeRepository.getUsers()
    }
}