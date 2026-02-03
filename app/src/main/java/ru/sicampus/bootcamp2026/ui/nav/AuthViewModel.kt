package ru.sicampus.bootcamp2026.ui.nav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.sicampus.bootcamp2026.domain.auth.AuthState
import ru.sicampus.bootcamp2026.domain.auth.GetAuthStatusUseCase

class AuthViewModel(
    getAuthStatusUseCase: GetAuthStatusUseCase
) : ViewModel() {
    val authState: StateFlow<AuthState> = getAuthStatusUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState.Loading
        )
}
class AuthViewModelFactory(
    private val getAuthStatusUseCase: GetAuthStatusUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(getAuthStatusUseCase) as T
    }
}