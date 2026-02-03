package ru.sicampus.bootcamp2026.data.source

import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMessageBuilder
import kotlinx.coroutines.flow.firstOrNull

suspend fun HttpMessageBuilder.addAuthHeader() {
    val token = AuthLocalDataSource.getToken().firstOrNull() ?: return
    header(HttpHeaders.Authorization, token)

}