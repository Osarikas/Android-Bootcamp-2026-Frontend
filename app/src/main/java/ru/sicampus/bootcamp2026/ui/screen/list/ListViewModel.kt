package ru.sicampus.bootcamp2026.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init{
        getData()
    }
    fun getData(){
        viewModelScope.launch {
            _uiState.emit(ListState.Loading)
            getEmployeesUseCase.invoke().fold(
                onSuccess = {data ->
                    _uiState.emit(ListState.Content(data))
                },
                onFailure = {e ->
                    _uiState.emit(ListState.Error(e.message.orEmpty()))
                }
            )

        }
    }
}