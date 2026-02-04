package ru.sicampus.bootcamp2026.ui.screen.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navController: NavController,
    onNavigateToRegister: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.actionFlow.collect{action ->
            when(action) {
                is LoginAction.OpenScreen -> navController.navigate(action.route)
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Привет! Давай авторизуемся",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        when (val currentState = state) {
            is LoginState.Data -> Content(viewModel, currentState, onNavigateToRegister)
            is LoginState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Composable
private fun Content(
    viewModel: LoginViewModel,
    state: LoginState.Data,
    onNavigateToRegister: () -> Unit
) {
    var inputLogin by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    val focusPasswordRequester = remember { FocusRequester() }
    Spacer(modifier = Modifier.size(16.dp))
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = inputLogin,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusPasswordRequester.requestFocus()
            }
        ),
        onValueChange = {
            inputLogin = it
            viewModel.onIntent(LoginIntent.TextInput(inputLogin, inputPassword))
        },
        label = { Text("Логин") }
    )
    Spacer(modifier = Modifier.size(16.dp))
    TextField(
        modifier = Modifier.focusRequester(focusPasswordRequester).fillMaxWidth(),
        value = inputPassword,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.onIntent(LoginIntent.Send(inputLogin, inputPassword))
            }
        ),
        onValueChange = {
            inputPassword = it
            viewModel.onIntent(LoginIntent.TextInput(inputLogin, inputPassword))
        },
        label = { Text("Пароль") }
    )
    Spacer(modifier = Modifier.size(16.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = onNavigateToRegister) {
            Text("Нет аккаунта? Зарегистрироваться")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onIntent(LoginIntent.Send(inputLogin, inputPassword))
            },
            enabled = state.isEnabledSend
        ) {
            Text("Войти")
        }

        if (state.error != null) {
            Text(
                modifier = Modifier,
                text = state.error,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red,
            )
        }
    }

}