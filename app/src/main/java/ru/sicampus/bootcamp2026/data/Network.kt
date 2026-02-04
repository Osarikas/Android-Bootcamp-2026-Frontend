package ru.sicampus.bootcamp2026.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.sicampus.bootcamp2026.data.source.AuthLocalDataSource

object Network {
    const val HOST = "https://bootcamp-back.indx0.ru"

    val client by lazy {
        HttpClient(CIO) {
            // Чтобы валидатор перехватывал ошибки до того, как Ktor выбросит Exception
            expectSuccess = false

            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) { Log.d("KTOR", message) }
                }
                level = LogLevel.ALL
            }

            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status == HttpStatusCode.Unauthorized) {
                        AuthLocalDataSource.clearToken()
                        throw IllegalStateException("Unauthorized")
                    }
                }
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}