package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.Network
import ru.sicampus.bootcamp2026.data.dto.EmployeeDTO
import ru.sicampus.bootcamp2026.data.source.util.addAuthHeader

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
}