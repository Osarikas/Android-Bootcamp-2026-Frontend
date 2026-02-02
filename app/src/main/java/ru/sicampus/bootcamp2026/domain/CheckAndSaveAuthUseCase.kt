package ru.sicampus.bootcamp2026.domain

import ru.sicampus.bootcamp2026.data.LoginRepository

class CheckAndSaveAuthUseCase(
    private val loginRepository: LoginRepository,

) {
    suspend operator fun invoke(
        login: String,
        password: String,
    ): Result<Unit> {
        return loginRepository.checkAndAuth(login, password).mapCatching { isLogin ->
            if(!isLogin) error("Login or password incorrect")

        }
    }
}