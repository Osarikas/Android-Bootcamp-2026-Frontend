package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.dto.EmployeeDTO

class EmployeeListDataSource {
    suspend fun getUser(query: String?): Result<List<EmployeeDTO>> = withContext(Dispatchers.IO){
        runCatching {
            val result = Network.client.get("${Network.HOST}/api/employee/all"){
                if (!query.isNullOrBlank()) {
                    parameter("search", query)
                }
            }
            if (result.status != HttpStatusCode.OK){
                error("Status: ${result.status}")
            }
            result.body()
        }
    }
}