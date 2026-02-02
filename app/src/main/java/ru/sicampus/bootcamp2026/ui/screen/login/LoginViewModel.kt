package ru.sicampus.bootcamp2026.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.innovationcampus.android.data.source.AuthLocalDataSource
import ru.innovationcampus.android.data.source.LoginNetworkDataSource
import ru.sicampus.bootcamp2026.data.LoginRepository
import ru.sicampus.bootcamp2026.domain.CheckAndSaveAuthUseCase
import ru.sicampus.bootcamp2026.domain.CheckAuthFormatUseCase

class LoginViewModel : ViewModel() {
    private val checkAuthFormatUseCase by lazy { CheckAuthFormatUseCase() }
    private val checkAndSaveAuthCodeUseCase by lazy { CheckAndSaveAuthUseCase(
        LoginRepository(
            loginNetworkDataSource = LoginNetworkDataSource(),
            authLocalDataSource = AuthLocalDataSource
        )
    ) }
    private val _uiState = MutableStateFlow<LoginState>(
        LoginState.Data(
            isEnabledSend = false,
            error = null
        )
    )
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Send -> {
                viewModelScope.launch {
                    checkAndSaveAuthCodeUseCase.invoke(intent.login, intent.password).
                    onSuccess {
                        //TODO
                    }.onFailure { error ->
                        updateStateIfData { oldState ->
                            oldState.copy(
                                error = error.message
                            )

                        }

                    }
                }
            }
            is LoginIntent.TextInput -> {
                updateStateIfData { oldState ->
                    oldState.copy(
                        isEnabledSend = checkAuthFormatUseCase.invoke(
                            intent.login,
                            intent.password
                        ),
                        error = null
                    )
                }
            }
        }
    }
    private fun updateStateIfData(lambda: (LoginState.Data) -> LoginState) {
        _uiState.update { state ->
            (state as? LoginState.Data)?.let { lambda.invoke(it) } ?: state
        }

    }
}