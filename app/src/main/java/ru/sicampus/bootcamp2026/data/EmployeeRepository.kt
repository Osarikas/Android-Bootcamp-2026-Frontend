package ru.sicampus.bootcamp2026.data

import ru.sicampus.bootcamp2026.data.source.EmployeeListDataSource
import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity

class EmployeeRepository(
    private val employeeListDataSource: EmployeeListDataSource
) {
    suspend fun getUsers(): Result<List<EmployeeEntity>>{
        return employeeListDataSource.getUser().map{ listDTO ->
            listDTO.mapNotNull { employeeDTO ->
                EmployeeEntity(
                    name = employeeDTO.name ?: return@mapNotNull null,
                    position = employeeDTO.position ?: return@mapNotNull null,
                    username = employeeDTO.username ?: return@mapNotNull null,
                    email = employeeDTO.email ?: return@mapNotNull null,
                    phoneNumber = employeeDTO.phoneNumber ?: return@mapNotNull null,
                    photoUrl = employeeDTO.photoUrl ?: return@mapNotNull null
                )
            }

        }
    }
}