package ru.sicampus.bootcamp2026.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.data.AuthRepository
import ru.sicampus.bootcamp2026.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.data.source.AuthNetworkDataSource
import ru.sicampus.bootcamp2026.domain.auth.LogoutEmployeeUseCase

class ProfileViewModel : ViewModel() {
    private val logoutEmployeeUseCase = LogoutEmployeeUseCase(
        authRepository = AuthRepository(
            authNetworkDataSource = AuthNetworkDataSource(),
            authLocalDataSource = AuthLocalDataSource
        )
    )
    fun logout(){
        viewModelScope.launch {
            logoutEmployeeUseCase.invoke()
        }
    }
}