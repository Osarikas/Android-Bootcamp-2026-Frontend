package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginNetworkDataSource {
    suspend fun checkAuth(): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching {
            val result = Network.client.post("${Network.HOST}/api/employee/login"){
                addAuthHeader()
            }
            result.status == HttpStatusCode.OK
        }
    }
}