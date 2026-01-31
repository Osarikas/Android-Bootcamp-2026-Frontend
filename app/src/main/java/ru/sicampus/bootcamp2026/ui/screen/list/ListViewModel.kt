package ru.sicampus.bootcamp2026.ui.screen.list

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
import ru.sicampus.bootcamp2026.data.source.EmployeeListDataSource
import ru.sicampus.bootcamp2026.domain.GetEmployeesUseCase

class ListViewModel : ViewModel() {
    private val getEmployeesUseCase = GetEmployeesUseCase(
        employeeRepository = EmployeeRepository(EmployeeListDataSource())
    )
    private val _uiState : MutableStateFlow<ListState> = MutableStateFlow(ListState.Loading)
    val uiState = _uiState.asStateFlow()

    val searchState = TextFieldState()

    init{
        observeSearch()
    }
    fun getData(query: String? = ""){

        viewModelScope.launch {
            if (_uiState.value !is ListState.Content) {
                _uiState.emit(ListState.Loading)
            }
            getEmployeesUseCase.invoke(query).fold(
                onSuccess = {data ->
                    _uiState.emit(ListState.Content(data))
                },
                onFailure = {e ->
                    _uiState.emit(ListState.Error(e.message.orEmpty()))
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
                    getData(query)
                }
        }
    }

}