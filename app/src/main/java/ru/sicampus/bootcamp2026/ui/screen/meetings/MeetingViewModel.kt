package ru.sicampus.bootcamp2026.ui.screen.meetings

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.data.EmployeeRepository
import ru.sicampus.bootcamp2026.data.source.EmployeeSearchDataSource
import ru.sicampus.bootcamp2026.domain.SearchEmployeesUseCase

class MeetingViewModel : ViewModel() {
    private val searchEmployeesUseCase by lazy{
        SearchEmployeesUseCase(
            employeeRepository = EmployeeRepository(EmployeeSearchDataSource())
        )
    }
    private val _uiState : MutableStateFlow<EmployeeSearchState> =
        MutableStateFlow(EmployeeSearchState.Loading)
    val uiState = _uiState.asStateFlow()

    val searchState = TextFieldState()
    private val validationRegex = Regex("^[a-zA-Zа-яА-ЯёЁ\\s]*$")

    init{
        observeSearch()
    }
    fun getData(query: String? = ""){

        viewModelScope.launch {
            if (_uiState.value !is EmployeeSearchState.Content) {
                _uiState.emit(EmployeeSearchState.Loading)
            }
            searchEmployeesUseCase.invoke(5, query = query).fold(
                onSuccess = {data ->
                    _uiState.emit(EmployeeSearchState.Content(data))
                },
                onFailure = {e ->
                    _uiState.emit(EmployeeSearchState.Error(e.message.orEmpty()))
                }
            )
        }

    }
    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            snapshotFlow { searchState.text }
                .map { it.toString() }
                .distinctUntilChanged()
                .debounce(400)
                .collect { query ->
                    if (query.matches(validationRegex)){
                        getData(query)
                    }
                    else{
                        val currentUsers = (uiState.value as? EmployeeSearchState.Content)?.users ?: emptyList()
                        _uiState.emit(EmployeeSearchState.Content(currentUsers, "Строка поиска содержит недопустимые символы"))
                    }
                }
        }
    }

}