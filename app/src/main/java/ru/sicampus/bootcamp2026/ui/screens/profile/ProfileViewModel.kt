package ru.sicampus.bootcamp2026.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.data.AuthRepository
import ru.sicampus.bootcamp2026.data.ProfileRepository
import ru.sicampus.bootcamp2026.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.data.source.AuthNetworkDataSource
import ru.sicampus.bootcamp2026.data.source.ProfileDataSource
import ru.sicampus.bootcamp2026.domain.auth.LogoutEmployeeUseCase
import ru.sicampus.bootcamp2026.domain.entities.EmployeeEntity
import ru.sicampus.bootcamp2026.domain.profile.GetProfileUseCase

class ProfileViewModel : ViewModel() {
    private val getProfileUseCase by lazy {
        GetProfileUseCase(
            profileRepository = ProfileRepository(
                profileDataSource = ProfileDataSource()
            )
        )
    }
    private val _uiState : MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getEmployeeData()
    }
    private val logoutEmployeeUseCase by lazy {
        LogoutEmployeeUseCase(
            authRepository = AuthRepository(
                authNetworkDataSource = AuthNetworkDataSource(),
                authLocalDataSource = AuthLocalDataSource
            )
        )
    }

    fun logout(){
        viewModelScope.launch {
            logoutEmployeeUseCase()
        }
    }
    fun getEmployeeData(){
        viewModelScope.launch {
            if (_uiState.value !is ProfileState.Content) {
                _uiState.emit(ProfileState.Loading)
            }
            getProfileUseCase().fold(
                onSuccess = { user ->
                    _uiState.emit(ProfileState.Content(user))
                },
                onFailure = { e ->
                    _uiState.emit(ProfileState.Error(e.message.orEmpty()))
                }
            )
        }

    }
    private fun handleUpdateField(updated: EmployeeEntity) {
        _uiState.update { state ->
            if (state is ProfileState.Content) {
                state.copy(user = updated)
            } else state
        }
    }
}