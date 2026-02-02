package ru.sicampus.bootcamp2026.data

import ru.innovationcampus.android.data.source.AuthLocalDataSource
import ru.innovationcampus.android.data.source.LoginNetworkDataSource

class LoginRepository(
    private val loginNetworkDataSource: LoginNetworkDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) {
    suspend fun checkAndAuth(
        login: String,
        password: String
    ): Result<Boolean>{
        AuthLocalDataSource.setToken(login, password)
        return loginNetworkDataSource.checkAuth(
            authLocalDataSource.token ?: return Result.success(false)
        ).onSuccess { isLogin ->
            if(!isLogin) authLocalDataSource.clearToken()

        }.onFailure {
            authLocalDataSource.clearToken()
        }

    }
}