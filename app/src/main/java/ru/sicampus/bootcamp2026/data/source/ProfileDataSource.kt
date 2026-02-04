package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.Network
import ru.sicampus.bootcamp2026.data.dto.EmployeeDTO
import ru.sicampus.bootcamp2026.data.dto.EmployeeProfileRequestDTO
import ru.sicampus.bootcamp2026.data.source.util.addAuthHeader
import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity

class ProfileDataSource {
    suspend fun getProfile(): Result<EmployeeDTO> = withContext(Dispatchers.IO) {
        runCatching {
            val result = Network.client.post("${Network.HOST}/api/employee/login"){
                addAuthHeader()
            }
            if(result.status != HttpStatusCode.OK){
                error("Error: $HttpStatusCode")
            }
            result.body()
        }
    }
    suspend fun editProfile(employeeProfileRequestDTO: EmployeeProfileRequestDTO): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val result = Network.client.patch("${Network.HOST}/api/employee"){
                addAuthHeader()
                setBody(employeeProfileRequestDTO)
            }
            if(result.status != HttpStatusCode.OK){
                error("Error: $HttpStatusCode")
            }
        }
    }
}