package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.Network
import ru.sicampus.bootcamp2026.data.dto.EmployeeProfileRequestDTO
import ru.sicampus.bootcamp2026.data.dto.EmployeeRequestDTO
import ru.sicampus.bootcamp2026.data.source.util.addAuthHeader

class AuthNetworkDataSource {
    suspend fun login(): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching {
            val result = Network.client.post("${Network.HOST}/api/employee/login"){
                addAuthHeader()
            }
            result.status == HttpStatusCode.OK
        }
    }
    suspend fun register(employeeProfileRequestDTO: EmployeeProfileRequestDTO): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val result = Network.client.post("${Network.HOST}/api/employee/register"){
                setBody(employeeProfileRequestDTO)
            }
            if (result.status.value != 200) {
                error("Error: ${result.status.value}")
            }
        }
    }
}