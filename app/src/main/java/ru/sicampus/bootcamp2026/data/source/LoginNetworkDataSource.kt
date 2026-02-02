package ru.innovationcampus.android.data.source

import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp2026.data.source.Network

class LoginNetworkDataSource {
    suspend fun checkAuth(token: String): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching {
            val result = Network.client.post("${Network.HOST}/api/employee/login"){
                header(HttpHeaders.Authorization, token)
            }
            result.status == HttpStatusCode.OK
        }
    }
}