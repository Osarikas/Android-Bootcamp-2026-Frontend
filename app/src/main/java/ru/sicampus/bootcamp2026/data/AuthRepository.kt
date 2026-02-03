package ru.sicampus.bootcamp2026.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.sicampus.bootcamp2026.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.data.source.LoginNetworkDataSource
import ru.sicampus.bootcamp2026.domain.auth.AuthState

class AuthRepository(
    private val loginNetworkDataSource: LoginNetworkDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) {
    suspend fun checkAndAuth(
        login: String,
        password: String
    ): Result<Boolean>{
        AuthLocalDataSource.setToken(login, password)
        return loginNetworkDataSource.checkAuth().onSuccess { isLogin ->
            if(!isLogin) authLocalDataSource.clearToken()

        }.onFailure {
            authLocalDataSource.clearToken()
        }

    }
    //suspend fun logout() {
    //    authLocalDataSource.clearToken()
    //}
    val authState: Flow<AuthState> = authLocalDataSource.getToken()
        .map { token ->
            if (token.isNullOrBlank()) AuthState.Unauthorized else AuthState.Authorized
        }
}